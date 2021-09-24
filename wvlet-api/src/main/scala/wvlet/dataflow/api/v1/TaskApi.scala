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
package wvlet.dataflow.api.v1

import wvlet.airframe.http.RPC
import wvlet.airframe.ulid.ULID
import wvlet.dataflow.api.v1.TaskApi.{Tags, TaskBody, TaskId}

import java.time.Instant

/**
  * A public API for submitting tasks
  */
@RPC
trait TaskApi {
  def newTask(request: TaskRequest): TaskRef
  def getTask(taskId: TaskId): Option[TaskRef]
  def listTasks(taskListRequest: TaskListRequest): TaskList
  def cancelTask(taskId: TaskId): Option[TaskRef]
}

object TaskApi {
  type TaskId   = String
  type TaskBody = Map[String, Any]
  object TaskBody {
    val empty: TaskBody = Map.empty
  }
  type Tags = Seq[String]
  object Tags {
    val empty: Tags = Seq.empty
  }
}

//sealed trait TaskTrigger
//object TaskTrigger {
//  case object ON_PARENT_SUCCESS extends TaskTrigger
//  case object ON_PARENT_FAILURE extends TaskTrigger
//
//  def unapply(s: String): Option[TaskTrigger] = Seq(ON_PARENT_SUCCESS, ON_PARENT_FAILURE).find(_.toString == s)
//}

case class TaskRequest(
    // Task plugin name
    taskPlugin: String,
    // A method name to execute in the plugin
    methodName: String,
    // taskBody is a serializable key:String -> value:Any pairs
    taskBody: TaskBody,
    // Tags attached to the task
    tags: Tags = Tags.empty,
    // Idempotent key for preventing duplicated task creation
    idempotentKey: ULID = ULID.newULID
)

/**
  * A reference of the task
  */
case class TaskRef(
    id: TaskId,
    taskPlugin: String,
    methodName: String,
    status: TaskStatus,
    tags: Tags,
    createdAt: Instant,
    updatedAt: Instant,
    completedAt: Option[Instant] = None,
    taskError: Option[TaskError] = None
) {
  override def toString: TaskId = {
    taskError match {
      case Some(err) =>
        s"[${id}] ${status}:${err.errorCode} ${taskPlugin}.${methodName}: ${err.message}"
      case None =>
        s"[${id}] ${status}: ${taskPlugin}.${methodName}"
    }
  }
  def isFailed: Boolean = status == TaskStatus.FAILED

  def withStatus(newStatus: TaskStatus): TaskRef = this.copy(status = newStatus)
  def withError(error: TaskError): TaskRef       = this.copy(taskError = Some(error), status = TaskStatus.FAILED)
  def withError(errorCode: ErrorCode, message: String): TaskRef =
    this.copy(taskError = Some(TaskError(errorCode, message)), status = TaskStatus.FAILED)
  def withError(errorCode: ErrorCode, message: String, cause: Throwable): TaskRef =
    this.copy(taskError = Some(TaskError(errorCode, message, Option(cause))), status = TaskStatus.FAILED)
}
case class TaskListRequest(
    limit: Option[Int] = None
)

case class TaskList(
    tasks: Seq[TaskRef],
    timestamp: Instant = Instant.now()
)

case class TaskError(errorCode: ErrorCode, message: String, cause: Option[Throwable] = None) {
  override def toString: String = {
    s"[${errorCode}] ${message}"
  }
}
