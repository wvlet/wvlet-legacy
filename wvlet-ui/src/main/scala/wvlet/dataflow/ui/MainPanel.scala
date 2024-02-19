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
package wvlet.dataflow.ui

import org.scalajs.dom
import wvlet.airframe.http.Http
import wvlet.airframe.rx.Rx
import wvlet.airframe.rx.html.*
import wvlet.airframe.rx.html.all.*
import wvlet.dataflow.api.frontend.FrontendRPC
import wvlet.dataflow.ui.component.Editor

import java.net.http.HttpClient

class MainPanel extends RxElement:

  private val rpcClient = FrontendRPC.newRPCAsyncClient(Http.client.newJSClient)

  override def render: RxElement =
    div(
      cls -> "p-2",
      "Hello wvlet!",
//      Rx.intervalMillis(500).flatMap { i =>
//        rpcClient.FrontendApi.serviceInfo().map { serviceInfo =>
//          p(s"Server up time:${serviceInfo.upTime}")
//        }
//      },
      new Editor()
    )
