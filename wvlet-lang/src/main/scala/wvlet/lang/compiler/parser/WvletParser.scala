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

import wvlet.lang.model.expression.Expression
import wvlet.lang.model.expression.Expression.{ConditionalExpression, Identifier, QName}
import wvlet.lang.model.logical.LogicalPlan
import wvlet.lang.model.logical.LogicalPlan.{FLOWRQuery, ForItem, Return, Where}
import WvletParser.EOFToken
import wvlet.log.LogSupport

import scala.annotation.tailrec

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

        val next = peekNextToken

        // TODO parse group by, join, etc.
        var whereClause: Option[Where]   = None
        var returnClause: Option[Return] = None
        next.token match {
          case Token.WHERE =>
            whereClause = Some(parseWhere)
            peekNextToken.token match {
              case Token.RETURN =>
                returnClause = Some(parseReturn)
              case _ =>
            }

          case Token.RETURN =>
            returnClause = Some(parseReturn)
          case _ =>
        }
        FLOWRQuery(forItems = forItems, whereClause, returnClause)(currentToken.getSourceLocation)
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
      parseIn
      val expr = parseExpression
      items += ForItem(id, expr)(currentToken.getSourceLocation)
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

  private def parseWhere: Where =
    val currentToken = peekNextToken
    if (currentToken.token == Token.WHERE) {
      nextToken
      val expr = parseExpression
      Where(expr)(currentToken.getSourceLocation)
    } else {
      parseError(currentToken, Token.WHERE)
    }

  private def parseReturn: Return =
    val currentToken = peekNextToken
    if (currentToken.token == Token.RETURN) {
      nextToken
      val exprs = parseExpressions
      Return(exprs)(currentToken.getSourceLocation)
    } else {
      parseError(currentToken, Token.RETURN)
    }

  private def parseExpressions: Seq[Expression] =
    val expr = parseExpression
    peekNextToken.token match {
      case Token.COMMA =>
        nextToken
        expr +: parseExpressions
      case _ =>
        Seq(expr)
    }

  private def parseExpression: Expression =
    val currentToken = peekNextToken
    currentToken.token match {
      case Token.IDENTIFIER =>
        val qName = parseQualifiedName
        val op    = peekNextToken
        op.token match {
          // expression comparisonOperator expression
          case Token.EQ | Token.NEQ | Token.LT | Token.LTEQ | Token.GT | Token.GTEQ =>
            val ops = op.token
            nextToken
            val rhs = parseExpression
            ops match {
              case Token.EQ   => Expression.Equal(qName, rhs)
              case Token.NEQ  => Expression.NotEqual(qName, rhs)
              case Token.LT   => Expression.LessThan(qName, rhs)
              case Token.LTEQ => Expression.LessThanOrEqual(qName, rhs)
              case Token.GT   => Expression.GreaterThan(qName, rhs)
              case Token.GTEQ => Expression.GreaterThanOrEqual(qName, rhs)
              case other =>
                parseError(op, Token.EQ)
            }
          case _ =>
            qName
        }
      case Token.INTEGER_LITERAL =>
        Expression.IntegerLiteral(currentToken.text, currentToken.text.toInt)(currentToken.getSourceLocation)
      case _ =>
        parseError(currentToken, Token.IDENTIFIER)
    }

  private def parseQualifiedName: Expression = {
    val qNameBuffer = Seq.newBuilder[String]
    val firstToken  = peekNextToken

    @tailrec
    def loop(t: TokenData): Expression = {
      t.token match {
        case Token.IDENTIFIER =>
          qNameBuffer += t.text
          nextToken
          loop(peekNextToken)
        case Token.DOT =>
          nextToken
          loop(peekNextToken)
        case _ =>
          val qName = qNameBuffer.result()
          if (qName.isEmpty) {
            parseError(t, Token.IDENTIFIER)
          } else {
            QName(qName)(firstToken.getSourceLocation)
          }
      }
    }

    loop(firstToken)
  }

  private def parseIdentifierRest(lastToken: TokenData): Expression =
    val currentToken = peekNextToken
    currentToken.token match {
      case Token.DOT =>
        nextToken
        val next = peekNextToken
        next.token match {
          case Token.IDENTIFIER =>
            nextToken
            parseIdentifierRest(next)
          case _ =>
            parseError(next, Token.IDENTIFIER)
        }
      case _ =>
        Identifier(lastToken.text)(lastToken.getSourceLocation)
    }
