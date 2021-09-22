/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wvlet.dataflow.server.coordinator

import wvlet.airframe.Design
import wvlet.airframe.metrics.ElapsedTime
import wvlet.airframe.ulid.ULID
import wvlet.dataflow.api.v1.TaskApi.{TaskId, TaskRef, TaskRequest}
import wvlet.dataflow.api.v1.TaskStatus
import wvlet.dataflow.server.coordinator.TaskManager._
import wvlet.dataflow.server.util.{RPCClientProvider, ScheduledThreadManager}
import wvlet.log.LogSupport

import java.time.Instant
import java.util.concurrent.{ConcurrentHashMap, TimeUnit}
import javax.annotation.PostConstruct
import scala.jdk.CollectionConverters._
import scala.util.Random

case class TaskManagerConfig(
    expiresAfterDone: ElapsedTime = ElapsedTime("10m"),
    maxQueueingTime: ElapsedTime = ElapsedTime("1d")
)

/**
  */
class TaskManager(
    config: TaskManagerConfig,
    nodeManager: NodeManager,
    rpcClientProvider: RPCClientProvider,
    threadManager: TaskManagerThreadManager,
    listeners: Seq[TaskStateListener] = Seq(TaskStateListener.defaultListener)
) extends LogSupport {

  // idempotent key -> TaskRef
  private val registeredTasks = new ConcurrentHashMap[ULID, TaskId]().asScala
  private val taskRefs        = new ConcurrentHashMap[TaskId, TaskRef].asScala
  private val taskRequests    = new ConcurrentHashMap[TaskId, TaskRequest].asScala

  @PostConstruct
  def start: Unit = {
    threadManager.scheduledAtFixedRate(0, 1, TimeUnit.SECONDS) {
      checkPendingTasks
    }
  }

  private def checkPendingTasks: Unit = {
    for {
      queuedTask        <- taskRefs.values.filter(_.status == TaskStatus.QUEUED);
      queuedTaskRequest <- taskRequests.get(queuedTask.id)
    } {
      val queuedTime = ElapsedTime.nanosSince(queuedTask.createdAt.getNano)
      if (queuedTime.compareTo(config.maxQueueingTime) > 0) {
        // Fail the task when it exceeds the max queueing time
        updateTask(queuedTask.id)(_.withStatus(TaskStatus.FAILED))
      } else {
        dispatchTask(queuedTaskRequest)
      }
    }
  }

  def dispatchTask(request: TaskRequest): TaskRef = {
    info(s"New task: ${request}")
    val taskRef = getOrCreateTask(request)

    val activeWorkerNodes = nodeManager.listWorkerNodes
    if (activeWorkerNodes.isEmpty) {
      warn(s"[${taskRef.id}] No worker node is available")
      taskRef
    } else {
      val nodeIndex         = Random.nextInt(activeWorkerNodes.size)
      val targetWorkerNode  = activeWorkerNodes(nodeIndex)
      val workerClient      = rpcClientProvider.getWorkerClient(targetWorkerNode.serverAddress)
      val updatedTask       = updateTask(taskRef.id)(_.withStatus(TaskStatus.STARTING))
      val taskExecutionInfo = workerClient.WorkerApi.runTask(taskRef.id, request)
      info(taskExecutionInfo)
      getTaskRef(taskRef.id).getOrElse(updatedTask)
    }
  }

  def getTaskRef(taskId: TaskId): Option[TaskRef] = {
    taskRefs.get(taskId)
  }

  def getOrCreateTask(request: TaskRequest): TaskRef = {
    val taskId = registeredTasks.getOrElseUpdate(
      request.idempotentKey, {
        // Add "T" prefix for the readability of taskId
        val taskId = s"T_${ULID.newULIDString}"
        val now    = Instant.now

        val ref = TaskRef(
          parentId = request.parentId,
          id = taskId,
          taskPlugin = request.taskPlugin,
          methodName = request.methodName,
          status = TaskStatus.QUEUED,
          tags = request.tags,
          createdAt = now,
          updatedAt = now,
          completedAt = None
        )
        taskRefs += taskId -> ref
        taskId
      }
    )
    taskRequests += taskId -> request
    taskRefs(taskId)
  }

  def updateTask(taskId: TaskId)(updater: TaskRef => TaskRef): TaskRef = {
    taskRefs.get(taskId) match {
      case Some(originalTaskRef) =>
        val newTask = updater(originalTaskRef)
        taskRefs += taskId -> newTask
        if (originalTaskRef.status != newTask.status) {
          listeners.foreach(_.onStateChange(newTask))
        }
        newTask
      case None =>
        throw new IllegalArgumentException(s"Unknown ${taskId}")
    }
  }

  def getAllTasks: Seq[TaskRef] = {
    taskRefs.values.toSeq
  }

}

object TaskManager {
  type TaskManagerThreadManager = ScheduledThreadManager

  def design: Design =
    Design.newDesign
      .bind[TaskManagerThreadManager].toInstance(new ScheduledThreadManager(name = "task-manager"))
}
