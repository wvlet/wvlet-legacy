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
package wvlet.lang.parsing

import wvlet.airframe.control.IO
import wvlet.airspec.AirSpec
import wvlet.log.io.Resource

class WvletScannerTest extends AirSpec:

  case class TestQuery(name: String, query: String)

  private def testQuery(path:String): Seq[TestQuery] =
    Resource
      .listResources(path)
      .filterNot(_.isDirectory)
      .map: f =>
        val name = f.logicalPath.split("/").last.stripSuffix(".wv")
        val query = IO.readAsString(f.url)
        TestQuery(name, query)

  test("scan test query files"):
    testQuery("query/basic").map: q =>
      q

  test("scan tokens"):
    testQuery("query/basic").map: q =>
      WvletScanner.scan(q.query)


  test("get tokens"):
    val tokens = WvletScanner.scan(
      s"""schema A:
         |  id: int,
         |  name: string
         |""".stripMargin)
    tokens shouldBe Seq(
      // schema A:
      Token.SCHEMA,
      Token.IDENTIFIER,
      Token.COLON,
      // Token.NEWLINE,
      //   id: int,
      Token.INDENT,
      Token.IDENTIFIER,
      Token.COLON,
      //   name: string
      Token.IDENTIFIER,
      Token.COLON,
      Token.IDENTIFIER
    )
