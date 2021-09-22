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

import wvlet.flow.api.v1.TaskApi.{TaskId, TaskList}
import wvlet.flow.api.v1.{TaskApi, TaskStatus}
import wvlet.flow.server.coordinator.NodeManager
import wvlet.flow.server.util.RPCClientProvider
import wvlet.log.LogSupport

import scala.util.Random

/**
  */
class TaskApiImpl(taskManager: TaskManager, nodeManager: NodeManager, rpcClientProvider: RPCClientProvider)
    extends TaskApi
    with LogSupport {

  override def newTask(request: TaskApi.TaskRequest): TaskApi.TaskRef = {
    info(s"New task: ${request}")
    val taskRef = taskManager.getOrCreateTask(request)

    val activeWorkerNodes = nodeManager.listWorkerNodes
    if (activeWorkerNodes.isEmpty) {
      throw new IllegalStateException(s"No worker node available")
    } else {
      val nodeIndex        = Random.nextInt(activeWorkerNodes.size)
      val targetWorkerNode = activeWorkerNodes(nodeIndex)
      val workerClient     = rpcClientProvider.getWorkerClient(targetWorkerNode.serverAddress)
      workerClient.WorkerApi.runTask(taskRef.id, request)
      taskManager.updateTask(taskRef.id)(_.withStatus(TaskStatus.STARTING))
    }
    taskRef
  }

  override def getTask(taskId: TaskId): Option[TaskApi.TaskRef] = ???

  override def listTasks(taskListRequest: TaskApi.TaskListRequest): TaskApi.TaskList = {
    TaskList(
      tasks = taskManager.getAllTasks
    )
  }

  override def cancelTask(taskId: TaskId): Option[TaskApi.TaskRef] = ???
}
