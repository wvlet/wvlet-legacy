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
package wvlet.lang.cli

import wvlet.airframe.launcher.{Launcher, command, option}
import wvlet.dataflow.api.BuildInfo
import wvlet.log.LogSupport

object WvletMain:
  def main(args: Array[String]): Unit =
    Launcher.of[WvletMain].execute(args)

  def execute(argLine: String): Unit =
    Launcher.of[WvletMain].execute(argLine)

class WvletMain(
    @option(prefix = "-h,--help", description = "display help messages", isHelp = true)
    displayHelp: Boolean
) extends LogSupport:
  info(s"wvlet: version ${BuildInfo.version} ${BuildInfo.builtAtString}")

  @command(description = "Start a UI server")
  def ui(): Unit = {
    // TODO
  }
