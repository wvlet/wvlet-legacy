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
package wvlet.dataflow.server.worker

import wvlet.airframe.rx.Cancelable
import wvlet.dataflow.api.internal.coordinator.CoordinatorApi.UpdateTaskRequest
import wvlet.dataflow.api.internal.worker.WorkerApi
import wvlet.dataflow.api.internal.worker.WorkerApi.TaskExecutionInfo
import wvlet.dataflow.api.v1.TaskApi._
import wvlet.dataflow.api.v1.{ErrorCode, TaskError, TaskList, TaskListRequest, TaskRef, TaskRequest, TaskStatus}
import wvlet.dataflow.server.PluginManager
import wvlet.dataflow.server.ServerModule.CoordinatorClient
import wvlet.dataflow.server.worker.WorkerService.WorkerSelf
import wvlet.dataflow.spi.{TaskInput, TaskPlugin}
import wvlet.log.LogSupport

import java.time.Instant

/**
  */
class WorkerApiImpl(node: WorkerSelf, coordinatorClient: CoordinatorClient, pluginManager: PluginManager)
    extends WorkerApi
    with LogSupport {
  override def runTask(taskId: TaskId, task: TaskRequest): WorkerApi.TaskExecutionInfo = {
    warn(s"run task: ${task}")
    pluginManager.getPlugin(task.taskPlugin) match {
      case Some(plugin) =>
        warn(s"found a plugin: ${plugin}")
        coordinatorClient.CoordinatorApi.updateTaskStatus(UpdateTaskRequest(taskId, TaskStatus.RUNNING))
        info(s"Running ${task.taskPlugin}:${task.methodName}")
      // runTaskInternal(plugin, TaskInput(taskId, task))
      case None =>
        val err = TaskError(ErrorCode.UNKNOWN_PLUGIN, s"Unknown plugin: ${task.taskPlugin}")
        coordinatorClient.CoordinatorApi.updateTaskStatus(
          UpdateTaskRequest(
            taskId,
            TaskStatus.FAILED,
            Some(err)
          )
        )
    }
    TaskExecutionInfo(taskId, nodeId = node.name, startedAt = Instant.now())
  }

  private def runTaskInternal(plugin: TaskPlugin, taskInput: TaskInput): Cancelable = {
    try {
      plugin.run(taskInput)
      coordinatorClient.CoordinatorApi.updateTaskStatus(
        UpdateTaskRequest(taskInput.taskId, TaskStatus.FINISHED)
      )
      // TODO Support cancellation
      Cancelable.empty
    } catch {
      case e: Throwable =>
        error(s"Task failed", e)
        coordinatorClient.CoordinatorApi.updateTaskStatus(
          UpdateTaskRequest(
            taskInput.taskId,
            TaskStatus.FAILED,
            Some(TaskError(ErrorCode.INTERNAL_ERROR, e.getMessage, cause = Some(e)))
          )
        )
        // TODO Support cancellation
        Cancelable.empty
    }
  }

  override def getTask(taskId: TaskId): Option[TaskRef]      = ???
  override def cancelTask(taskId: TaskId): Option[TaskRef]   = ???
  override def listTasks(request: TaskListRequest): TaskList = ???
}
