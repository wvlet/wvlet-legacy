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
import wvlet.flow.api.v1.{TaskList, TaskListRequest, TaskRef, TaskRequest, WorkerApi}

/**
  */
class WorkerApiImpl extends WorkerApi {
  override def runTask(task: TaskRequest): WorkerApi.TaskExecutionInfo = ???
  override def getTask(taskId: TaskId): Option[TaskRef]                = ???
  override def cancelTask(taskId: TaskId): Option[TaskRef]             = ???
  override def listTasks(request: TaskListRequest): TaskList           = ???
}
