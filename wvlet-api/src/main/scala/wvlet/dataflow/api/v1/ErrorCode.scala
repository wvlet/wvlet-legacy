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

abstract class ErrorCode(val code: Int, val retryable: Boolean) {
  def name: String = this.toString

  def newException(message: String): DataflowException = {
    DataflowException(this, message)
  }
  def newException(message: String, cause: Throwable): DataflowException = {
    DataflowException(this, message, Some(cause))
  }
}

abstract class RetryableError(code: Int)    extends ErrorCode(code, retryable = true)
abstract class NonRetryableError(code: Int) extends ErrorCode(code, retryable = false)

object ErrorCode {
  // Errors in user inputs
  case object USER_ERROR     extends NonRetryableError(0x0000)
  case object SYNTAX_ERROR   extends NonRetryableError(0x0001)
  case object UNKNOWN_PLUGIN extends NonRetryableError(0x0002)

  // Internal service errors, which is usually retryable
  case object INTERNAL_ERROR    extends RetryableError(0x1_0000)
  case object TOO_MANY_REQUESTS extends RetryableError(0x1_0001)

  // External service errors (e.g., errors in third-party APIs)
  case object EXTERNAL_ERROR extends RetryableError(0x2_0000)
}
