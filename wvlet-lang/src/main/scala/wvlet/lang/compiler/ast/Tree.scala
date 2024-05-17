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
package wvlet.lang.compiler.ast

import wvlet.lang.compiler.core.Name
import wvlet.lang.compiler.core.Name.EmptyName

abstract class Tree:
  private var _span: Span = Span.NoSpan

  def withSpan(span: Span): this.type =
    if _span == span then this
    else
      if !_span.exists then if span.exists then _span = span
      this

object Tree:

  case object empty extends Tree

  // Relational operator
  case class RelOp(input: Tree, name: Name, args: List[Tree])        extends Tree
  // Code block
  case class Block(statements: Seq[Tree], lastExpr: Tree)            extends Tree
  // Import statement
  case class Import(prefix: Symbol, selectors: List[ImportSelector]) extends Tree

  case class FunctionApply(func: Tree, args: List[Tree]) extends Tree
  case class Ref(expr: Tree, name: Name) extends Tree


  case class Identifier(name: Name) extends Tree
  class Literal(stringValue: String, dataType: ) extends Tree
  case class NullLiteral() extends Literal
  case class IntLiteral(value: Long) extends Literal
  case class LongLiteral(value: Long) extends Literal
  case class StringLiteral(value: String) extends Literal
  case class BooleanLiteral(value: Boolean) extends Literal
  case class FloatLiteral(value: Float) extends Literal
  case class DoubleLiteral(value: Double) extends Literal


  case class ImportSelector(imported: Identifier, renamed: Name = EmptyName) extends Tree:
    def isWildcard: Boolean = imported.name == Name.WILDCARD
