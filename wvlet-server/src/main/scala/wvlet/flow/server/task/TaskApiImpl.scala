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

import wvlet.airframe.ulid.ULID
import wvlet.flow.api.v1.{TaskApi, TaskStatus}
import wvlet.flow.api.v1.TaskApi.{TaskId, TaskRef}
import wvlet.flow.server.RPCClientProvider
import wvlet.flow.server.cluster.NodeManager
import wvlet.log.LogSupport

import java.time.Instant

/**
  */
class TaskApiImpl(taskManager: TaskManager, nodeManager: NodeManager, rpcClientProvider: RPCClientProvider)
    extends TaskApi
    with LogSupport {

  override def newTask(request: TaskApi.TaskRequest): TaskApi.TaskRef = {
    info(s"New task: ${request}")

    // Add "T:" prefix for the readability of taskId
    val taskId = s"T:${ULID.newULIDString}"
    val now    = Instant.now

    TaskRef(
      parentId = request.parentId,
      id = taskId,
      status = TaskStatus.QUEUED,
      tags = request.tags,
      createdAt = now,
      updatedAt = now,
      completedAt = None
    )
  }

  override def getTask(taskId: TaskId): Option[TaskApi.TaskRef] = ???

  override def listTasks(taskListRequest: TaskApi.TaskListRequest): TaskApi.TaskList = ???

  override def cancelTask(taskId: TaskId): Option[TaskApi.TaskRef] = ???
}
