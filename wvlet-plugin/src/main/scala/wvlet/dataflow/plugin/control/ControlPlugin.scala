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
package wvlet.dataflow.plugin.control

import wvlet.airframe.codec.MessageCodec
import wvlet.airframe.rx.Cancelable
import wvlet.dataflow.api.v1.{DataflowException, ErrorCode, TaskRequest}
import wvlet.dataflow.spi.{PluginContext, TaskInput, TaskPlugin}
import wvlet.log.LogSupport

object ControlPlugin extends TaskPlugin {

  case class AndThenTask(firstTask: TaskRequest, nextTask: TaskRequest) extends LogSupport {
    def run: Unit = {
      warn(this)
      val context = PluginContext.current

    }
  }

  override def pluginName: String = "control"

  override def run(input: TaskInput): Cancelable = {
    input.methodName match {
      case "andThen" =>
        val t = MessageCodec.of[AndThenTask].fromMap(input.taskBody)
        t.run
      case other =>
        throw DataflowException(ErrorCode.UNKNOWN_METHOD, s"unknown method: ${other}")
    }
    Cancelable.empty
  }
}
