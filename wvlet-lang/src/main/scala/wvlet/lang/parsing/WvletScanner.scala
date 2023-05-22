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

trait ScannerSource {
  def text: String
}
case class SourceFile(path: String) extends ScannerSource {
  override def text: String = IO.readAsString(new java.io.File(path))
}
case class StringSource(override val text: String) extends ScannerSource

object WvletScanner {

  def scan(text: String): Seq[Token] = {
    val scanner = new WvletScanner(StringSource(text))
    // TODO
    Seq.empty
  }

}

class WvletScanner(source: ScannerSource) {
  private var cursor: Int = -1

  def nextChar(): Unit = {}

  def skipToken(): Unit = {}

  def nextToken(): Token = {
    ???
  }

  // def insertToken(token: Token, offset: Int): Unit = { ... }
}
