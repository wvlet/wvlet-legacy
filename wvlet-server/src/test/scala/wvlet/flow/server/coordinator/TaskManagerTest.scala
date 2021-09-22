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
package wvlet.flow.server.coordinator

import wvlet.airframe.Design
import wvlet.airspec.AirSpec
import wvlet.flow.api.v1.TaskApi.{TaskId, TaskListRequest, TaskRef, TaskRequest}
import wvlet.flow.api.v1.TaskStatus
import wvlet.flow.server.ServerModule
import wvlet.flow.server.ServerModule.{ApiClient, CoordinatorClient}

import scala.annotation.tailrec

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
    debug(ret)

    val taskList = client.TaskApi.listTasks(TaskListRequest())

    @tailrec
    def loop(t: TaskId, maxRetry: Int): Unit = {
      if (maxRetry < 0) {
        fail("Cannot update the status")
      } else {
        client.TaskApi.getTask(t) match {
          case None =>
            fail(s"Task ${t} is not found")
          case Some(ref) if ref.status != TaskStatus.QUEUED =>
          // ok
          case Some(ref) =>
            Thread.sleep(100)
            loop(t, maxRetry - 1)
        }
      }
    }

    loop(ret.id, 30)

    debug(taskList)
  }
}
