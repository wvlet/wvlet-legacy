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
package wvlet.lang.parser

import wvlet.airspec.AirSpec

class WvletParserTest extends AirSpec {
  private val p = new WvletParser()

  test("parse") {
    val plan = p.parse("""for x in db.tbl
        |return x
        |""".stripMargin)
    info(plan)

    p.parse("for r in db")
  }

  test("parse single-line for") {
    p.parse("""for x in tbl1, y in tbl2""")
  }

  test("parse indent") {
    p.parse("""for
              |  x in tbl1
              |  y in tbl2
              |""".stripMargin)
  }
}