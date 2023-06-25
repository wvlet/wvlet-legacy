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
package wvlet.lang.compiler.parser

import wvlet.airspec.AirSpec
import wvlet.lang.compiler.parser.{Token, TokenData, WvletScanner}

class WvletScannerTest extends AirSpec with QuerySuite:

  test("s1: get tokens"):
    val tokens = WvletScanner.scan(s"""schema A:
         |  id: int,
         |  name: string
         |""".stripMargin)
    trace(tokens.mkString("\n"))
    tokens shouldMatch {
      case List(
            // schema A:
            TokenData(Token.SCHEMA, "schema", _, _),
            TokenData(Token.IDENTIFIER, "A", _, _),
            TokenData(Token.COLON, ":", _, _),
            // Token.NEWLINE,
            //   id: int,
            // TokenData(Token.INDENT, _, _, _),
            TokenData(Token.IDENTIFIER, "id", _, _),
            TokenData(Token.COLON, ":", _, _),
            TokenData(Token.IDENTIFIER, "int", _, _),
            TokenData(Token.COMMA, ",", _, _),
            //   name: string
            TokenData(Token.IDENTIFIER, "name", _, _),
            TokenData(Token.COLON, ":", _, _),
            TokenData(Token.IDENTIFIER, "string", _, _)
          ) =>
    }

  runSuite { q =>
    val tokens = WvletScanner.scan(q.query)
    debug(s"Query ${q.name}:\n${tokens.mkString("\n")}")
  }
