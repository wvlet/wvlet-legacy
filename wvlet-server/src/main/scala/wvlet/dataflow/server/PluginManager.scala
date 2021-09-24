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

import wvlet.airframe.surface.Surface
import wvlet.airframe.{Design, Session}
import wvlet.dataflow.api.v1.TaskApi.TaskId
import wvlet.dataflow.api.v1.{DataflowException, ErrorCode, TaskRef, TaskRequest}
import wvlet.dataflow.plugin.control.ControlPlugin
import wvlet.dataflow.plugin.sqlite.SQLitePlugin
import wvlet.dataflow.plugin.trino.TrinoPlugin
import wvlet.dataflow.server.ServerModule.ApiClient
import wvlet.dataflow.spi.{PluginContext, TaskPlugin}
import wvlet.log.LogSupport

import java.util.concurrent.ConcurrentHashMap
import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

class PluginManager(session: Session) extends LogSupport {
  private val plugins = new ConcurrentHashMap[String, TaskPlugin]().asScala

  def addPlugin(plugin: TaskPlugin): Unit = {
    plugins.getOrElseUpdate(plugin.pluginName, plugin)
  }

  def addPluginOfSurface(pluginSurface: Surface): Unit = {
    session.get[TaskPlugin](pluginSurface) match {
      case plugin: TaskPlugin =>
        addPlugin(plugin)
      case _ =>
        warn(s"Failed to instantiate plugin: ${pluginSurface.name}")
    }
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
        x.addPluginOfSurface(Surface.of[ControlPlugin])
        x.addPlugin(SQLitePlugin)
        x.addPlugin(TrinoPlugin)
      }
      .bind[PluginContext].to[PluginContextImpl]
}

class PluginContextImpl(client: ApiClient) extends PluginContext with LogSupport {
  override def newTask(taskRequest: TaskRequest): TaskRef = {
    client.TaskApi.newTask(taskRequest)
  }

  override def await(taskId: TaskId): TaskRef = {
    debug(s"Awaiting task completion: ${taskId}")
    @tailrec
    def loop(): TaskRef = {
      client.TaskApi.getTask(taskId) match {
        case None =>
          throw DataflowException(ErrorCode.UNKNOWN_TASK, s"Unknown task: ${taskId}")
        case Some(t) if t.isDone =>
          t
        case _ =>
          Thread.sleep(100)
          loop()
      }
    }
    loop()
  }
}
