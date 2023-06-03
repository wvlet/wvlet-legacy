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
import wvlet.lang.parsing.{ScannerSource, StringSource, WvletScanner}
import wvlet.lang.parsing.Token

import scala.annotation.{switch, tailrec}
import scala.collection.mutable

trait ScannerSource {
  def text: String
  def length: Int
}
case class SourceFile(path: String) extends ScannerSource {
  override lazy val text: String = IO.readAsString(new java.io.File(path))
  override def length: Int = text.length
}
case class StringSource(override val text: String) extends ScannerSource {
  override val
  length: Int = text.length
}



object WvletScanner {

  def scan(text: String): Seq[Token] = {
    val scanner = new WvletScanner(StringSource(text))
    // TODO
    Seq.empty
  }

  inline val LF = '\u000A'
  inline val FF = '\u000C'
  inline val CR = '\u000D'
  inline val SU = '\u001A'
}

class WvletScanner(source: ScannerSource) {
  import WvletScanner.*

  private var cursor: Int = 0
  private var ch: Char = 0
  protected val tokenBuffer = TokenBuffer()

  def skipToken(): Unit = {}

  def nextToken(): Token =
    if(cursor > source.length)
      Token.EOF
    else
      fetchToken()

  private def nextChar(): Unit =
    cursor += 1

  private def putChar(ch: Char): Unit =
    tokenBuffer.append(ch)

  private def lookAheadChar(): Char =
    source.text.charAt(cursor + 1)

  private def fetchToken(): Token =
    val ch = source.text.charAt(cursor)
    (ch: @switch) match
      case ' ' | '\t' | CR | LF | FF =>
        // Skip white space characters
        nextChar()
        fetchToken()
      case 'A' | 'B' | 'C' | 'D' | 'E' |
           'F' | 'G' | 'H' | 'I' | 'J' |
           'K' | 'L' | 'M' | 'N' | 'O' |
           'P' | 'Q' | 'R' | 'S' | 'T' |
           'U' | 'V' | 'W' | 'X' | 'Y' |
           'Z' | '$' | '_' |
           'a' | 'b' | 'c' | 'd' | 'e' |
           'f' | 'g' | 'h' | 'i' | 'j' |
           'k' | 'l' | 'm' | 'n' | 'o' |
           'p' | 'q' | 'r' | 's' | 't' |
           'u' | 'v' | 'w' | 'x' | 'y' |
           'z' =>
        putChar(ch)
        nextChar()
        getIdentRest()
      case '~' | '!' | '@' | '#' | '%' |
           '^' | '*' | '+' | '-' | /*'<' | */
           '>' | '?' | ':' | '=' | '&' |
           '|' | '\\'  =>
        putChar(ch)
        nextChar()
        getOperatorRest()


  @tailrec private def getOperatorRest(): Unit = (ch: @switch) match {
    case '~' | '!' | '@' | '#' | '%' |
         '^' | '*' | '+' | '-' | '<' |
         '>' | '?' | ':' | '=' | '&' |
         '|' | '\\' =>
      putChar(ch)
      nextChar()
      getOperatorRest()
    case '/' =>
      val nxch = lookAheadChar()
      if nxch == '/' || nxch == '*' then finishNamed()
      else {
        putChar(ch); nextChar(); getOperatorRest()
      }
    case _ =>
      if isSpecial(ch) then {
        putChar(ch); nextChar(); getOperatorRest()
      }
      else if isSupplementary(ch, isSpecial) then getOperatorRest()
      else finishNamed()
  }

  private def getIdentRest(): Token = (ch: @switch) match {
    case 'A' | 'B' | 'C' | 'D' | 'E' |
         'F' | 'G' | 'H' | 'I' | 'J' |
         'K' | 'L' | 'M' | 'N' | 'O' |
         'P' | 'Q' | 'R' | 'S' | 'T' |
         'U' | 'V' | 'W' | 'X' | 'Y' |
         'Z' | '$' |
         'a' | 'b' | 'c' | 'd' | 'e' |
         'f' | 'g' | 'h' | 'i' | 'j' |
         'k' | 'l' | 'm' | 'n' | 'o' |
         'p' | 'q' | 'r' | 's' | 't' |
         'u' | 'v' | 'w' | 'x' | 'y' |
         'z' |
         '0' | '1' | '2' | '3' | '4' |
         '5' | '6' | '7' | '8' | '9' =>
      putChar(ch)
      nextChar()
      getIdentRest()
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
}
