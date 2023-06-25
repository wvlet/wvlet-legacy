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

import wvlet.airframe.control.IO
import wvlet.lang.model.SourceLocation
import wvlet.log.LogSupport

import scala.annotation.{switch, tailrec}
import scala.collection.mutable

// TODO Add line, offset
case class TokenData(token: Token, text: String, start: Int, end: Int) {
  override def toString: String = s"[${start},${end}] '${text}' <${token}>"
  // TODO
  def getSourceLocation: Option[SourceLocation] = None
}

object WvletScanner extends LogSupport:
  def scan(text: String): Seq[TokenData] = {
    val scanner   = new WvletScanner(StringSource(text))
    val tokens    = Seq.newBuilder[TokenData]
    var tokenData = scanner.nextToken()
    while tokenData.token != Token.EOF do
      tokens += tokenData
      tokenData = scanner.nextToken()
    tokens.result()
  }

  inline val LF = '\u000A'
  inline val FF = '\u000C'
  inline val CR = '\u000D'
  inline val SU = '\u001A'

  private def isNumberSeparator(ch: Char): Boolean = ch == '_'

  /**
    * Convert a charactor to an integer value using the given base. Returns -1 upon failures
    */
  def digit2int(ch: Char, base: Int): Int = {
    val num =
      if (ch <= '9') then ch - '0'
      else if ('a' <= ch && ch <= 'z') ch - 'a' + 10
      else if ('A' <= ch && ch <= 'Z') ch - 'A' + 10
      else -1
    if (0 <= num && num < base) num else -1
  }

class WvletScanner(source: ScannerSource) extends LogSupport:
  import WvletScanner.*

  private var cursor: Int = 0

  /**
    */
  private var lineOffset: Int = -1
  protected val tokenBuffer   = TokenBuffer()

  private def reportError(msg: String, loc: SourceLocation): Unit = {
    error(s"${msg} at ${loc}")
  }

  private def checkNoTrailingNumberSeparator(): Unit = {
    if (tokenBuffer.nonEmpty && isNumberSeparator(tokenBuffer.last)) {
      reportError("trailing number separator", source.sourceLocationOf(cursor))
    }
  }

  // TODO Skip token until a safe location for error recovery
  def skipToken(): Unit = {}

  def nextToken(): TokenData =
    if (cursor >= source.length)
      TokenData(Token.EOF, "", cursor, cursor)
    else
      fetchToken()

  private def peekChar(): Char =
    if (cursor >= source.length)
      SU
    else
      source.text.charAt(cursor)

  private def nextChar(): Unit =
    cursor += 1

  private def putChar(ch: Char): Unit =
    tokenBuffer.append(ch)

  private def lookAheadChar(): Char =
    source.text.charAt(cursor + 1)

  private def fetchToken(): TokenData =
    val ch = peekChar()
    trace(s"fetchToken ch[${cursor}]: '${String.valueOf(ch)}'")
    (ch: @switch) match
      case ' ' | '\t' | CR | LF | FF =>
        // Skip white space characters
        nextChar()
        fetchToken()
      case 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H' | 'I' | 'J' | 'K' | 'L' | 'M' | 'N' | 'O' | 'P' | 'Q' | 'R' |
          'S' | 'T' | 'U' | 'V' | 'W' | 'X' | 'Y' | 'Z' | '$' | '_' | 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' |
          'i' | 'j' | 'k' | 'l' | 'm' | 'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' | 'z' =>
        putChar(ch)
        nextChar()
        getIdentRest()
      case '~' | '!' | '@' | '#' | '%' | '^' | '*' | '+' | '-' | /*'<' | */
          '>' | '?' | ':' | '=' | '&' | '|' | '\\' =>
        putChar(ch)
        nextChar()
        getOperatorRest()
      case '0' =>
        var base: Int = 10
        def fetchLeadingZero(): Unit = {
          nextChar()
          ch match {
            case 'x' | 'X' =>
              base = 16
              nextChar()
            case _ =>
              base = 10
              putChar('0')
          }
          val nextCh = peekChar()
          if (base != 10 && !isNumberSeparator(nextCh) && digit2int(nextCh, base) < 0)
            error("invalid literal number")
        }
        fetchLeadingZero()
        getNumber(base)
      case '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' =>
        getNumber(base = 10)
      case ',' =>
        nextChar()
        TokenData(Token.COMMA, ",", cursor - 1, cursor)
      case SU =>
        TokenData(Token.EOF, "", cursor, cursor)
      case _ =>
        putChar(ch)
        nextChar()
        toToken()

  @tailrec private def getOperatorRest(): TokenData =
    val ch = peekChar()
    trace(s"getOperatorRest[${cursor}]: ch: '${String.valueOf(ch)}'")
    (ch: @switch) match {
      case '~' | '!' | '@' | '#' | '%' | '^' | '*' | '+' | '-' | '<' | '>' | '?' | ':' | '=' | '&' | '|' | '\\' =>
        putChar(ch)
        nextChar()
        getOperatorRest()
//    case '/' =>
//      val nxch = lookAheadChar()
//      if nxch == '/' || nxch == '*' then finishNamed()
//      else {
//        putChar(ch); nextChar(); getOperatorRest()
//      }
      case SU =>
        toToken()
      case _ =>
        toToken()
//      if isSpecial(ch) then {
//        putChar(ch); nextChar(); getOperatorRest()
//      }
//      else if isSupplementary(ch, isSpecial) then getOperatorRest()
//      else finishNamed()
    }

  private def getIdentRest(): TokenData =
    val ch = peekChar()
    trace(s"getIdentRest[${cursor}]: ch: '${String.valueOf(ch)}'")
    (ch: @switch) match {
      case 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H' | 'I' | 'J' | 'K' | 'L' | 'M' | 'N' | 'O' | 'P' | 'Q' | 'R' |
          'S' | 'T' | 'U' | 'V' | 'W' | 'X' | 'Y' | 'Z' | '$' | 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' | 'i' |
          'j' | 'k' | 'l' | 'm' | 'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' | 'z' | '0' |
          '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' =>
        putChar(ch)
        nextChar()
        getIdentRest()
      case _ =>
        val token = toToken()
        token
//    case '_' =>
//      putChar(ch)
//      nextChar()
//      getIdentOrOperatorRest()
//    case SU => // strangely enough, Character.isUnicodeIdentifierPart(SU) returns true!
//      finishNamed()
//    case _ =>
//      if isUnicodeIdentifierPart(ch) then
//        putChar(ch)
//        nextChar()
//        getIdentRest()
//      else if isSupplementary(ch, isUnicodeIdentifierPart) then
//        getIdentRest()
//      else
//        finishNamed()
    }
  // def insertToken(token: Token, offset: Int): Unit = { ... }

  private def getAndClearTokenBuffer(): String = {
    val str = tokenBuffer.toString
    tokenBuffer.clear()
    str
  }

  private def toToken(): TokenData =
    val currentTokenStr = getAndClearTokenBuffer()
    trace(s"toToken at ${cursor}: '${currentTokenStr}'")
    Tokens.allKeywords.find(x => x.str == currentTokenStr) match {
      case Some(keyword) =>
        TokenData(keyword, currentTokenStr, cursor - currentTokenStr.length, cursor)
      case None =>
        TokenData(Token.IDENTIFIER, currentTokenStr, cursor - currentTokenStr.length, cursor)
    }

  private def getNumber(base: Int): TokenData = {
    var ch = peekChar()
    while (isNumberSeparator(ch) || digit2int(ch, base) >= 0) {
      putChar(ch)
      nextChar()
      ch = peekChar()
    }
    checkNoTrailingNumberSeparator()
    var tokenType = Token.INTEGER_LITERAL

    if (base == 10 && ch == '.') {
      val lch = peekChar()
      if ('0' <= lch && lch <= '9') {
        putChar('.')
        nextChar()
        tokenType = getFraction()
      }
    } else
      (ch: @switch) match {
        case 'e' | 'E' | 'f' | 'F' | 'd' | 'D' =>
          if (base == 10) then tokenType = getFraction()
        case 'l' | 'L' =>
          nextChar()
          tokenType = Token.LONG_LITERAL
        case _ =>
      }

    checkNoTrailingNumberSeparator()

    val strVal = getAndClearTokenBuffer()
    TokenData(tokenType, strVal, cursor - strVal.length, cursor)
  }

  private def getFraction(): Token = {
    var tokenType = Token.DECIMAL_LITERAL
    val ch        = peekChar()
    while ('0' <= ch && ch <= '9' || isNumberSeparator(ch)) {
      putChar(ch)
      nextChar()
    }
    checkNoTrailingNumberSeparator()
    if (ch == 'e' || ch == 'E') {
      putChar(ch)
      nextChar()
      var lookaheadCh = peekChar()
      if (lookaheadCh == '+' || lookaheadCh == '-') {
        putChar(ch)
        nextChar()
        lookaheadCh = peekChar()
      }
      if ('0' <= lookaheadCh && lookaheadCh <= '9' || isNumberSeparator(ch)) {
        putChar(ch)
        nextChar()
        if (ch == '+' || ch == '-') {
          putChar(ch)
          nextChar()
        }
        while ('0' <= ch && ch <= '9' || isNumberSeparator(ch)) {
          putChar(ch)
          nextChar()
        }
        checkNoTrailingNumberSeparator()
      }
      tokenType = Token.EXP_LITERAL
    }
    if (ch == 'd' || ch == 'D') {
      putChar(ch)
      nextChar()
      tokenType = Token.DOUBLE_LITERAL
    } else if (ch == 'f' || ch == 'F') {
      putChar(ch)
      nextChar()
      tokenType = Token.FLOAT_LITERAL
    }
    // checkNoLetter()
    tokenType
  }
