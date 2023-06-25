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

trait ScannerSource {
  def text: String
  def length: Int

  def sourceLocationOf(offset: Int): SourceLocation = {
    val pos = SourcePosition(this, offset)
    pos.toSourceLocation
  }
}
case class SourceFile(path: String) extends ScannerSource {
  override lazy val text: String = IO.readAsString(new java.io.File(path))
  override def length: Int       = text.length
}
case class StringSource(override val text: String) extends ScannerSource {
  override val length: Int = text.length
}

case class SourcePosition(source: ScannerSource, offset: Int) {
  def line: Int = {
    var i    = 0
    var line = 1
    while (i < offset) {
      if (source.text.charAt(i) == '\n') {
        line += 1
      }
      i += 1
    }
    line
  }
  def column: Int = {
    var i   = offset
    var col = 1
    while (i > 0 && source.text.charAt(i) != '\n') {
      col += 1
      i -= 1
    }
    col
  }
  def toSourceLocation: SourceLocation = SourceLocation(line, column)
}
