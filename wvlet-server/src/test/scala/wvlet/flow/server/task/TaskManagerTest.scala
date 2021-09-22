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

import wvlet.airframe.Design
import wvlet.airspec.AirSpec
import wvlet.flow.api.v1.TaskApi.{TaskListRequest, TaskRequest}
import wvlet.flow.server.ServerModule
import wvlet.flow.server.ServerModule.{ApiClient, CoordinatorClient}

/**
  */
class TaskManagerTest extends AirSpec {

  override def design: Design = ServerModule.testServerAndClient

  test("add a new task") { (client: ApiClient) =>
    val ret = client.TaskApi.newTask(
      TaskRequest(
        parentId = None,
        taskType = "trino",
        taskBody = Map("query" -> "select 1", "service" -> "TD (US)"),
        tags = Seq(s"service:td")
      )
    )

    val taskList = client.TaskApi.listTasks(TaskListRequest())
    info(taskList)

    info(ret)
  }
}
