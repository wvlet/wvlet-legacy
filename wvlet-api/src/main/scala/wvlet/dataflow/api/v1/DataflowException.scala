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
package wvlet.dataflow.api.v1

/**
  * A standard exception class that will be used across wvlet.dataflow modules.
  *
  * Usage:
  * {{{
  * throw new DataFlowException(ErrorCode.SYNTAX_ERROR, "Invalid syntax")
  * }}}
  */
case class DataflowException(errorCode: ErrorCode, message: String = "", cause: Option[Throwable] = None)
    extends Exception(s"[${errorCode.name}] ${message}", cause.getOrElse(null))
