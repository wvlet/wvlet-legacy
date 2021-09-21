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
import wvlet.flow.api.v1.CoordinatorApi.NewTaskRequest
import CoordinatorApi._
import wvlet.airframe.ulid.ULID
import wvlet.flow.api.v1.TaskRef.TaskId

import java.time.Instant

/**
  */
@RPC
trait CoordinatorApi {
  def newTask(request: NewTaskRequest): NewTaskResponse
  def getTask(taskId: TaskId): Option[TaskRef]

  def listTasks: TaskList

  def listNodes: Seq[NodeInfo]
  def register(node: Node): Unit
}

object CoordinatorApi {

  case class NewTaskRequest(
      name: String,
      taskContent: Map[String, Any],
      tags: Seq[String],
      idempotentKey: ULID = ULID.newULID
  )
  case class NewTaskResponse(
      taskId: TaskId
  )

  type NodeId = String

  case class Node(name: NodeId, address: String, isCoordinator: Boolean, startedAt: Instant)
  case class NodeInfo(node: Node, lastHeartbeatAt: Instant) {
    def isCoordinator: Boolean = node.isCoordinator
  }
}
