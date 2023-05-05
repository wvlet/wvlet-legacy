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
package wvlet.dataflow.api.internal.coordinator

import wvlet.airframe.http.*
import wvlet.dataflow.api.internal.Cluster.{Node, NodeInfo}
import wvlet.dataflow.api.internal.ServiceInfoApi
import wvlet.dataflow.api.v1.TaskApi.TaskId
import wvlet.dataflow.api.v1.{TaskError, TaskStatus}

import java.time.Instant

/**
  * Coordinator is a central manager of task execution control. It receives requests for running tasks.
  *
  * A coordinator can have multiple worker nodes for distributed processing.
  */
@RPC
trait CoordinatorApi extends ServiceInfoApi {
  import CoordinatorApi._

  def listNodes: Seq[NodeInfo]
  def register(node: Node): Unit
  def updateTaskStatus(request: UpdateTaskRequest): Unit
}

object CoordinatorApi extends RxRouterProvider {
  override def router: RxRouter = RxRouter.of[CoordinatorApi]
  case class UpdateTaskRequest(taskId: TaskId, status: TaskStatus, error: Option[TaskError] = None)
}
