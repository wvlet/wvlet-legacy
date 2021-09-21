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
package wvlet.flow.api.v1

import wvlet.airframe.http.RPC
import wvlet.flow.api.v1.CoordinatorApi.{NodeId}
import wvlet.flow.api.v1.TaskRef.TaskId
import java.time.Instant

/**
  * Worker receives a task request from the coordinator and process the task
  */
@RPC
trait WorkerApi {
  import WorkerApi._

  def runTask(task: TaskRequest): TaskExecutionInfo

  def getTask(taskId: TaskId): Option[TaskRef]
  def listTasks: TaskList
}

object WorkerApi {
  case class TaskExecutionInfo(taskId: TaskId, nodeId: NodeId, createdAt: Instant = Instant.now())
}
