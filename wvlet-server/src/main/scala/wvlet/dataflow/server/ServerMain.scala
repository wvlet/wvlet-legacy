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
import wvlet.airframe.http.Router
import wvlet.airframe.http.finagle.{Finagle, FinagleServer}
import wvlet.airframe.launcher.{Launcher, command, option}
import wvlet.dataflow.api.v1.ServiceApi
import wvlet.dataflow.server.api.ServerApi
import wvlet.log.{LogSupport, Logger}

/**
  *
  */
object ServerMain {
  def main(args: Array[String]): Unit = {
    Logger.init
    Launcher.of[ServerMain].execute(args)
  }

  def router =
    Router
      .add[ServiceApi]
      .add[ServerApi]

  def design =
    Design.newDesign
      .add {
        Finagle.server
          .withName("wvlet-server")
          .withPort(8080)
          .withRouter(router)
          .design
      }

}

class ServerMain(@option(prefix = "-h,--help", description = "Show help messages", isHelp = true) help: Boolean)
    extends LogSupport {

  @command(isDefault = true)
  def default: Unit = {
    info(s"Type --help to see the list of sub commands")
  }

  @command(description = "Start wvlet server")
  def server = {
    ServerMain.design.withProductionMode.build[FinagleServer] { server =>
      server.waitServerTermination
    }
  }

}
