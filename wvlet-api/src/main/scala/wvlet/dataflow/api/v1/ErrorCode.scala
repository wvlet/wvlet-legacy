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

import wvlet.airframe.surface.reflect.ReflectTypeUtil
import wvlet.log.LogSupport
import wvlet.log.io.Resource

abstract class ErrorCode(val code: Int, val retryable: Boolean) {
  def name: String = this.toString
}

abstract class RetryableError(code: Int)    extends ErrorCode(code, retryable = true)
abstract class NonRetryableError(code: Int) extends ErrorCode(code, retryable = false)

object ErrorCode extends LogSupport {

  lazy val all: Seq[ErrorCode] = {
    // Retrieve all ErrorCode classes using reflection
    for {
      cl  <- Resource.findClasses("wvlet.dataflow.api.v1", classOf[ErrorCode]);
      obj <- ReflectTypeUtil.companionObject(cl)
    } yield {
      obj.asInstanceOf[ErrorCode]
    }
  }

  private lazy val errorCodeTable   = all.map(x => x.name -> x).toMap
  private var unknownErrorCodeNames = Set.empty[String]

  def unapply(s: String): Option[ErrorCode] = {
    errorCodeTable.get(s).orElse {
      // Remember the unknown code name to suppress warning messages
      if (!unknownErrorCodeNames.contains(s)) {
        warn(s"Unknown error code: ${s}. Use ${UNKNOWN_ERROR} instead")
        unknownErrorCodeNames += s
      }
      Some(UNKNOWN_ERROR)
    }
  }

  // Errors in user inputs
  case object USER_ERROR     extends NonRetryableError(0x0000)
  case object SYNTAX_ERROR   extends NonRetryableError(0x0001)
  case object UNKNOWN_PLUGIN extends NonRetryableError(0x0002)
  case object UNKNOWN_METHOD extends NonRetryableError(0x0003)

  // Internal service errors, which is usually retryable
  case object INTERNAL_ERROR             extends RetryableError(0x1_0000)
  case object TOO_MANY_REQUESTS          extends RetryableError(0x1_0001)
  case object EXCEEDED_MAX_QUEUEING_TIME extends NonRetryableError(0x1_0002)

  // Deterministic internal errors, which are not retryable
  case object DETERMINISTIC_INTERNAL_ERROR extends NonRetryableError(0x2_0000)
  case object MISSING_PLUGIN_CONTEXT       extends NonRetryableError(0x2_0001)

  // External service errors (e.g., errors in third-party APIs)
  case object EXTERNAL_ERROR extends RetryableError(0x3_0000)

  // Deterministic external errors, which are not retryable
  case object DETERMINISTIC_EXTERNAL_ERROR extends NonRetryableError(0x4_0000)

  case object UNKNOWN_ERROR extends RetryableError(0xf_ffff)

}
