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

import wvlet.lang.model.expression.Expression
import wvlet.lang.model.logical.LogicalPlan
import wvlet.lang.model.logical.LogicalPlan.{FLOWRQuery, ForItem}
import wvlet.lang.parsing.WvletParser.EOFToken
import wvlet.log.LogSupport

object WvletParser:
  def parse(text: String): LogicalPlan =
    val tokens: Seq[TokenData] = WvletScanner.scan(text)
    val tokenScanner           = new TokenScanner(tokens.toIndexedSeq)
    val parser                 = new WvletParser(tokenScanner)
    parser.parseStatement

  val EOFToken = TokenData(Token.EOF, "", 0, 0)

class TokenScanner(tokens: IndexedSeq[TokenData]):
  private var cursor = 0

  def peekNext: TokenData =
    if (cursor < tokens.size)
      tokens(cursor)
    else
      WvletParser.EOFToken

  def next: Unit =
    cursor += 1

class WvletParser(tokenScanner: TokenScanner) extends LogSupport:

  private def parseError(token: TokenData, expected: Token): Nothing =
    // TODO define parse error exception type
    throw new IllegalArgumentException(s"parse error: expected ${expected}, but found ${token}")

  private def peekNextToken: TokenData =
    tokenScanner.peekNext

  private def nextToken: Unit =
    tokenScanner.next

  def parseStatement: LogicalPlan =
    val currentToken = peekNextToken
    currentToken.token match {
      case Token.FOR =>
        parseFLOWRQuery

      case Token.EOF =>
        null
      case _ =>
        null
    }

  def parseFLOWRQuery: FLOWRQuery =
    val currentToken = peekNextToken
    currentToken.token match {
      case Token.FOR =>
        nextToken
        val forItems: Seq[ForItem] = parseForItems
        // TODO parse where, return
        FLOWRQuery(forItems = forItems, None, None)(currentToken.getNodeLocation)
      case _ =>
        null
    }

  private def parseForItems: Seq[ForItem] =
    // identifier IN expr
    val items        = Seq.newBuilder[ForItem]
    var currentToken = peekNextToken
    while (currentToken.token == Token.IDENTIFIER) {
      val id = currentToken.text
      nextToken

      if (peekNextToken.token == Token.IN) {
        nextToken
      }

      val expr = parseExpression
      items += ForItem(id, expr)(currentToken.getNodeLocation)
      currentToken = peekNextToken
    }
    items.result

  private def parseIn: Unit = {
    val currentToken = peekNextToken
    if (currentToken.token == Token.IN) {
      nextToken
    } else {
      parseError(currentToken, Token.IN)
    }
  }

  private def parseExpression: Expression =
    val currentToken = peekNextToken
    // TODO
    null
