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
package wvlet.dataflow.server

import wvlet.airframe.Design
import wvlet.airframe.codec.MessageCodec
import wvlet.airspec.AirSpec
import wvlet.dataflow.api.v1.TaskRequest
import wvlet.dataflow.plugin.control.ControlPlugin.AndThenTask
import wvlet.dataflow.plugin.sqlite.SQLitePlugin
import wvlet.dataflow.plugin.sqlite.SQLitePlugin.RunQuery
import wvlet.dataflow.server.ServerModule.ApiClient
import wvlet.dataflow.server.util.TaskUtil

class ControlPluginTest extends AirSpec {
  override def design: Design = ServerModule.testServerAndClient

  test("run control task") { (client: ApiClient) =>
    val a = AndThenTask(
      firstTask = TaskRequest(
        taskPlugin = "sqlite",
        methodName = "runQuery",
        taskBody = MessageCodec
          .of[Map[String, Any]].fromMsgPack(
            MessageCodec
              .of[SQLitePlugin.RunQuery].toMsgPack(
                RunQuery("db1", "select 1")
              )
          )
      ),
      nextTask = TaskRequest(
        taskPlugin = "sqlite",
        methodName = "runQuery",
        taskBody = MessageCodec
          .of[Map[String, Any]].fromMsgPack(
            MessageCodec
              .of[SQLitePlugin.RunQuery].toMsgPack(
                RunQuery("db1", "select 2")
              )
          )
      )
    )

    val t = MessageCodec
      .of[Map[String, Any]].fromMsgPack(
        MessageCodec.of[AndThenTask].toMsgPack(a)
      )
    val taskRef = client.TaskApi.newTask(
      TaskRequest(
        taskPlugin = "control",
        methodName = "andThen",
        taskBody = t
      )
    )
    val lastState = TaskUtil.waitCompletion(client, taskRef.id)
    info(lastState)
  }
}
