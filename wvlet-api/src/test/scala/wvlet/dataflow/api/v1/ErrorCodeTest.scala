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

import wvlet.airspec.AirSpec
import wvlet.dataflow.api.v1.ErrorCode.UNKNOWN_ERROR
import wvlet.log.io.Resource

object ErrorCodeTest extends AirSpec {
  test("covers all existing error codes") {
    val allErrorCodesInTheClassPath = Resource.findClasses("wvlet.dataflow.api.v1", classOf[ErrorCode])
    allErrorCodesInTheClassPath.size shouldBe ErrorCode.all.size

    val expectedErrorCodeNames = allErrorCodesInTheClassPath.map(_.getSimpleName.replaceAll("\\$", "")).toSet
    for (e <- ErrorCode.all) {
      expectedErrorCodeNames.contains(e.name) shouldBe true
    }
  }

  test("parse error code") {
    for (e <- ErrorCode.all) {
      ErrorCode.unapply(e.name) shouldBe Some(e)
    }
  }

  test("Resolve unknown error") {
    for (i <- 0 to 5) {
      // This should show warning only once
      ErrorCode.unapply("DUMMY_ERROR_CODE") shouldBe Some(UNKNOWN_ERROR)
    }
  }
}
