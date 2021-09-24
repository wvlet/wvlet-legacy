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
import wvlet.dataflow.plugin.control.ControlPlugin
import wvlet.dataflow.plugin.sqlite.SQLitePlugin
import wvlet.dataflow.plugin.trino.TrinoPlugin
import wvlet.dataflow.spi.TaskPlugin

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters._

class PluginManager {
  private val plugins = new ConcurrentHashMap[String, TaskPlugin]().asScala

  def addPlugin(plugin: TaskPlugin): Unit = {
    plugins.getOrElseUpdate(plugin.pluginName, plugin)
  }

  def getPlugin(name: String): Option[TaskPlugin] = {
    plugins.get(name)
  }
}

object PluginManager {
  def design: Design =
    Design.newDesign
      .bind[PluginManager].toSingleton
      .onStart { x =>
        x.addPlugin(ControlPlugin)
        x.addPlugin(SQLitePlugin)
        x.addPlugin(TrinoPlugin)
      }
}
