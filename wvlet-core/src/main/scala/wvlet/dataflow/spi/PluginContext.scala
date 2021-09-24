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
package wvlet.dataflow.spi

import wvlet.dataflow.api.v1.{DataflowException, ErrorCode, TaskRef, TaskRequest}

trait PluginContext {
  def newTask(taskRequest: TaskRequest): TaskRef
}

object PluginContext {
  // TODO use TLS
  private var _current: Option[PluginContext] = None

  def current: PluginContext = {
    _current.getOrElse(
      throw DataflowException(ErrorCode.MISSING_PLUGIN_CONTEXT, "missing plugin context")
    )
  }

  private[dataflow] def setPluginContext(context: PluginContext) = {
    _current = Some(context)
  }
}
