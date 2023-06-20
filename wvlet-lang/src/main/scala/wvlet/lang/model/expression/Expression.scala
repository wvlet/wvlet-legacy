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
package wvlet.lang.model.expression

import wvlet.lang.model.NodeLocation
import wvlet.lang.model.formatter.PrintContext

trait Expression {
  def toExpr(context: PrintContext): String
  def nodeLocation: Option[NodeLocation] = None
}

object Expression:
  case class Identifier(name: String)(nodeLocation: Option[NodeLocation]) extends Expression {
    override def toExpr(context: PrintContext): String = name
  }
  case class QName(names: Seq[String])(nodeLocation: Option[NodeLocation]) extends Expression {
    override def toExpr(context: PrintContext) = names.mkString(".")
  }

  sealed trait ConditionalExpression extends Expression
  case class Equal(left: Expression, right: Expression) extends ConditionalExpression {
    override def toExpr(context: PrintContext) = s"${left.toExpr(context)} = ${right.toExpr(context)}"
  }
  case class NotEqual(left: Expression, right: Expression) extends ConditionalExpression {
    override def toExpr(context: PrintContext) = s"${left.toExpr(context)} != ${right.toExpr(context)}"
  }
  case class LessThan(left: Expression, right: Expression) extends ConditionalExpression {
    override def toExpr(context: PrintContext) = s"${left.toExpr(context)} < ${right.toExpr(context)}"
  }
  case class LessThanOrEqual(left: Expression, right: Expression) extends ConditionalExpression {
    override def toExpr(context: PrintContext) = s"${left.toExpr(context)} <= ${right.toExpr(context)}"
  }
  case class GreaterThan(left: Expression, right: Expression) extends ConditionalExpression {
    override def toExpr(context: PrintContext) = s"${left.toExpr(context)} > ${right.toExpr(context)}"
  }
  case class GreaterThanOrEqual(left: Expression, right: Expression) extends ConditionalExpression {
    override def toExpr(context: PrintContext) = s"${left.toExpr(context)} >= ${right.toExpr(context)}"
  }
