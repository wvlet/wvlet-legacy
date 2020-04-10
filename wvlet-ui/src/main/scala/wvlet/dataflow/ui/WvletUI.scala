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

import wvlet.airframe.Design
import wvlet.log.{LogLevel, LogSupport, Logger}

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom._
import wvlet.airframe.http.rx.html.DOMRenderer
import wvlet.dataflow.ui.component.MainPanel

/**
  *
  */
object WvletUI extends LogSupport {
  def design = Design.newDesign

  @JSExport
  def main(args: Array[String]): Unit = {
    Logger.setDefaultLogLevel(LogLevel.INFO)
    Logger("wvlet.dataflow.ui").setLogLevel(LogLevel.DEBUG)
    //Logger("wvlet.airframe.http").setLogLevel(LogLevel.DEBUG)

    debug(s"started")
    initializeUI
  }

  def initializeUI: Unit = {
    // Insert main node if not exists
    val mainNode = document.getElementById("main") match {
      case null =>
        val elem = dom.document.createElement("div")
        elem.setAttribute("id", "main")
        document.body.appendChild(elem)
      case other => other
    }

    val session = design.noLifeCycleLogging.newSession
    val panel   = session.build[MainPanel]
    DOMRenderer.renderTo(mainNode, panel)
    session.start
  }

}
