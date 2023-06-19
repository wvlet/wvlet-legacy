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

import wvlet.lang.model.logical.LogicalPlan
import wvlet.log.LogSupport

object WvletParser:
  def parse(text: String): LogicalPlan =
    val tokens: Seq[TokenData] = WvletScanner.scan(text)
    val parser                 = new WvletParser
    parser.parse(tokens)


class TokenScanner(tokens: IndexedSeq[TokenData]):
  private var cursor = 0
  def peek: TokenData =
    tokens(cursor)

  def next: Unit =
    cursor += 1

class WvletParser() extends LogSupport:
  def parse(tokens: Seq[TokenData]): LogicalPlan =
    val scanner = new TokenScanner(tokens.toIndexedSeq)
    null
