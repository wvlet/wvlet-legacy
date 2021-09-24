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
import wvlet.airspec.AirSpec
import wvlet.dataflow.api.v1.TaskRequest
import wvlet.dataflow.server.ServerModule.ApiClient

class ControlPluginTest extends AirSpec {
  override def design: Design = ServerModule.testServerAndClient

  test("run control task") { (client: ApiClient) =>
//    val taskRef = client.TaskApi.newTask(
//      TaskRequest(
//      )
//    )

  }
}
