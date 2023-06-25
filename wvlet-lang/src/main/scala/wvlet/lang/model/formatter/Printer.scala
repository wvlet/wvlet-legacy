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
package wvlet.lang.model.formatter

case class PrintContext(
    indentLevel: Int = 0,
    formatOption: FormatOption
) {
  def newline: String             = "\n"
  def indent(offset: Int): String = " " * (indentLevel + offset)

  def indentBlock(s: String): String = {
    s.split("""\n""")
      .map { line =>
        s"${formatOption.indent(indentLevel)}$line"
      }
      .mkString("\n")
  }
  def withIndent: PrintContext                                   = copy(indentLevel = indentLevel + 1)
  def withOutdent: PrintContext                                  = copy(indentLevel = indentLevel - 1)
  def withIndentLevel(level: Int): PrintContext                  = copy(indentLevel = level)
  def withFormatOption(formatOption: FormatOption): PrintContext = copy(formatOption = formatOption)
}

object PrintContext:
  val default = PrintContext(indentLevel = 0, formatOption = FormatOption.default)

/**
  * Print the logical plan
  */
class Printer {}
