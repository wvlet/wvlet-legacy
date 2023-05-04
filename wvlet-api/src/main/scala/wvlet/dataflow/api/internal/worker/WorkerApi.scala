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
package wvlet.dataflow.api.internal.worker

import wvlet.airframe.http._
import wvlet.dataflow.api.internal.Cluster.NodeId
import wvlet.dataflow.api.internal.ServiceInfoApi
import wvlet.dataflow.api.v1.TaskApi.TaskId
import wvlet.dataflow.api.v1._

import java.time.Instant

/**
  * Worker receives a task request from the coordinator and process the task
  */
@RPC
trait WorkerApi extends ServiceInfoApi {
  import WorkerApi._
  def hello: String = "hello"

  def runTask(taskId: TaskId, task: TaskRequest): TaskExecutionInfo
  def getTask(taskId: TaskId): Option[TaskRef]
  def cancelTask(taskId: TaskId): Option[TaskRef]
  def listTasks(request: TaskListRequest): TaskList
}

object WorkerApi extends RxRouterProvider {
  override def router: RxRouter = RxRouter.of[WorkerApi]
  case class TaskExecutionInfo(taskId: TaskId, nodeId: NodeId, startedAt: Instant = Instant.now())
}
