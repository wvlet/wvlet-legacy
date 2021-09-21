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
package wvlet.flow.server.api

import wvlet.flow.api.v1.TaskRef.TaskId
import wvlet.flow.api.v1.{CoordinatorApi, TaskList, TaskListRequest, TaskRef, TaskRequest}
import wvlet.flow.server.NodeManager

/**
  */
class CoordinatorApiImpl(nodeManager: NodeManager) extends CoordinatorApi {
  override def newTask(request: TaskRequest): TaskRef                = ???
  override def getTask(taskId: TaskId): Option[TaskRef]              = ???
  override def listTasks(taskListRequest: TaskListRequest): TaskList = ???
  override def cancelTask(taskId: TaskId): Option[TaskRef]           = ???

  override def listNodes: Seq[CoordinatorApi.NodeInfo] = {
    nodeManager.listNodes
  }
  override def register(node: CoordinatorApi.Node): Unit = {
    nodeManager.heartBeat(node)
  }
}
