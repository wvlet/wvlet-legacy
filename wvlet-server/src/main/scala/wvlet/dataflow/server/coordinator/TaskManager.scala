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
import wvlet.dataflow.api.v1.TaskApi.TaskId
import wvlet.dataflow.api.v1.{TaskRef, TaskRequest, TaskStatus}
import wvlet.dataflow.server.util.{RPCClientProvider, ScheduledThreadManager}
import wvlet.log.LogSupport

import java.util.concurrent.{ConcurrentHashMap, LinkedBlockingQueue, TimeUnit}
import javax.annotation.PostConstruct
import scala.jdk.CollectionConverters._
import scala.util.Random

case class TaskManagerConfig(
    maxTaskHistory: Int = 10000,
    expiresAfterDone: ElapsedTime = ElapsedTime("10m"),
    maxQueueingTime: ElapsedTime = ElapsedTime("1d")
)

/**
  */
class TaskManager(
    config: TaskManagerConfig,
    nodeManager: NodeManager,
    rpcClientProvider: RPCClientProvider,
    listeners: Seq[TaskStateListener] = Seq(TaskStateListener.defaultListener)
) extends AutoCloseable
    with LogSupport {

  private val threadManager = new ScheduledThreadManager(name = "task-manager")

  // idempotent key -> TaskRef
  private val registeredTasks = new ConcurrentHashMap[ULID, TaskId]().asScala
  private val taskRefs        = new ConcurrentHashMap[TaskId, TaskRef].asScala
  private val taskRequests    = new ConcurrentHashMap[TaskId, TaskRequest].asScala
  private val expiredTasks    = new LinkedBlockingQueue[TaskRef]().asScala

  @PostConstruct
  def start: Unit = {
    // Start a background thread that
    threadManager.scheduleWithFixedDelay(0, 1, TimeUnit.SECONDS) { () =>
      checkPendingTasks
    }
  }

  override def close(): Unit = {
    threadManager.close()
  }

  private def checkPendingTasks: Unit = {
    // Wake up queued tasks
    for {
      queuedTask        <- taskRefs.values if queuedTask.status == TaskStatus.QUEUED;
      queuedTaskRequest <- taskRequests.get(queuedTask.id)
    } {
      dispatchTaskInternal(queuedTask, queuedTaskRequest)
    }

    // Remove expired tasks
    for (task <- taskRefs.values if task.status.isDone) {}

    //
//      val queuedTime = ElapsedTime.nanosSince(TimeUnit.SECONDS.toNanos(queuedTask.createdAt.getEpochSecond))
//      if (queuedTime.compareTo(config.maxQueueingTime) > 0) {
//        warn(s"${queuedTime} is exceeded")
//        // Fail the task when it exceeds the max queueing time
//        updateTask(queuedTask.id)(
//          _.withError(
//            TaskError(ErrorCode.EXCEEDED_MAX_QUEUEING_TIME, s"${queuedTime} exceeded ${config.maxQueueingTime}")
//          )
//        )
//      } else {
//        val queuedTaskRequest = taskRequests(queuedTask.id)
//        warn(s"Found queued: ${queuedTaskRequest}")
//        dispatchTaskInternal(queuedTask, queuedTaskRequest)
//      }
  }

  def dispatchTask(request: TaskRequest): TaskRef = {
    debug(s"New task: ${request}")
    val taskRef = getOrCreateTask(request)
    dispatchTaskInternal(taskRef, request)
  }

  private def dispatchTaskInternal(taskRef: TaskRef, taskRequest: TaskRequest): TaskRef = {
    val activeWorkerNodes = nodeManager.listWorkerNodes
    if (activeWorkerNodes.isEmpty) {
      warn(s"[${taskRef.id}] No worker node is available")
      taskRef
    } else {
      debug(s"[${taskRef.id}] Dispatch task")
      val nodeIndex         = Random.nextInt(activeWorkerNodes.size)
      val targetWorkerNode  = activeWorkerNodes(nodeIndex)
      val workerClient      = rpcClientProvider.getWorkerClient(targetWorkerNode.serverAddress)
      val updatedTask       = updateTask(taskRef.id)(_.withStatus(TaskStatus.STARTING))
      val taskExecutionInfo = workerClient.WorkerApi.runTask(taskRef.id, taskRequest)
      debug(taskExecutionInfo)
      getTaskRef(taskRef.id).getOrElse(updatedTask)
    }
  }

  def getTaskRef(taskId: TaskId): Option[TaskRef] = {
    taskRefs.get(taskId)
  }

  def getOrCreateTask(request: TaskRequest): TaskRef = {
    val taskId = registeredTasks.getOrElseUpdate(
      request.idempotentKey, {
        ULID.newULID
      }
    )
    val taskRef = taskRefs.getOrElseUpdate(
      taskId, {
        val now = taskId.toInstant
        TaskRef(
          id = taskId,
          taskPlugin = request.taskPlugin,
          methodName = request.methodName,
          status = TaskStatus.QUEUED,
          tags = request.tags,
          updatedAt = now,
          completedAt = None
        )
      }
    )
    taskRequests.getOrElseUpdate(taskId, request)
    taskRef
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

  def design: Design = Design.newDesign
    .bind[TaskManager].toSingleton

}
