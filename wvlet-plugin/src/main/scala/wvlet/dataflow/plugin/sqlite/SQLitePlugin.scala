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
package wvlet.dataflow.plugin.sqlite

import wvlet.airframe.codec.MessageCodec
import wvlet.airframe.http.RPCStatus
import wvlet.airframe.rx.Cancelable
import wvlet.dataflow.spi.{TaskInput, TaskPlugin}
import wvlet.log.LogSupport

object SQLitePlugin extends TaskPlugin:
  override def pluginName: String = "sqlite"

  case class RunQuery(service: String, query: String, schema: Option[String] = None) extends LogSupport:
    def run: Unit =
      info(s"run sqlite query\n${query}")

  override def run(input: TaskInput): Cancelable =
    input.methodName match
      case "runQuery" =>
        val command = MessageCodec.of[RunQuery].fromMap(input.taskBody)
        command.run
      case other =>
        throw RPCStatus.NOT_FOUND_U5.newException(s"unknown method: ${other}")
    // TODO Support cancellation
    Cancelable.empty
