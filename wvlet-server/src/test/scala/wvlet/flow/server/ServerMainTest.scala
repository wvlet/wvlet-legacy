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
package wvlet.flow.server

import wvlet.airframe.Design
import wvlet.airframe.ulid.ULID
import wvlet.airspec.AirSpec
import wvlet.flow.server.ServerModule.CoordinatorClient

/**
  */
class ServerMainTest extends AirSpec {

  override def design: Design = ServerModule.testServerAndClient

  test("launch server") { (client: CoordinatorClient) =>
    test("service info") {
      val serviceInfo = client.v1.ServiceInfoApi.serviceInfo()
      debug(serviceInfo)
    }

    test("node list") {
      val list = client.v1.CoordinatorApi.listNodes()
      list.size > 0 shouldBe true
      list.find(_.isCoordinator) shouldBe defined
    }

    test("launch id") {
      val launchId = client.v1.ServiceInfoApi.launchId()
      launchId.epochMillis <= System.currentTimeMillis() shouldBe true
    }
  }

}
