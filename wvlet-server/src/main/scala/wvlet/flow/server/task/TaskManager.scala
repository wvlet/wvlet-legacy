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
package wvlet.flow.server.task

import TaskManager._
import wvlet.airframe.Design
import wvlet.airframe.metrics.ElapsedTime
import wvlet.airframe.ulid.ULID
import wvlet.flow.api.v1.TaskApi.{TaskId, TaskRef, TaskRequest}
import wvlet.flow.api.v1.TaskStatus
import wvlet.flow.server.ThreadManager

import java.time.Instant
import java.util.concurrent.{ConcurrentHashMap, ConcurrentMap}
import scala.jdk.CollectionConverters._

case class TaskManagerConfig(
    expiresAfterDone: ElapsedTime = ElapsedTime("10m")
)

/**
  */
class TaskManager(threadManager: TaskManagerThreadManager) {

  // idempotent key -> TaskRef
  private val registeredTasks = new ConcurrentHashMap[ULID, TaskId]().asScala
  private val taskRefs        = new ConcurrentHashMap[TaskId, TaskRef].asScala
  private val taskRequests    = new ConcurrentHashMap[TaskId, TaskRequest].asScala

  def getOrCreateTask(request: TaskRequest): TaskRef = {
    val taskId = registeredTasks.getOrElseUpdate(
      request.idempotentKey, {
        // Add "T:" prefix for the readability of taskId
        val taskId = s"T:${ULID.newULIDString}"
        val now    = Instant.now

        val ref = TaskRef(
          parentId = request.parentId,
          id = taskId,
          taskType = request.taskType,
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

  def updateTask(taskId: TaskId)(updater: TaskRef => TaskRef): Unit = {
    taskRefs.get(taskId) match {
      case Some(taskRef) =>
        taskRefs += taskId -> updater(taskRef)
      case None =>
        throw new IllegalArgumentException(s"Unknown ${taskId}")
    }
  }

  def getAllTasks: Seq[TaskRef] = {
    taskRefs.values.toSeq
  }

}

object TaskManager {
  type TaskManagerThreadManager = ThreadManager

  def design: Design =
    Design.newDesign
      .bind[TaskManagerThreadManager].toInstance(new ThreadManager(name = "task-manager"))
}
