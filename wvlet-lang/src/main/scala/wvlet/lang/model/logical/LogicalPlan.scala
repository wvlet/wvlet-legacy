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
package wvlet.lang.model.logical

import wvlet.lang.model.NodeLocation
import wvlet.lang.model.expression.*

trait LogicalPlan {
  def nodeLocation: Option[NodeLocation] = None
}

object LogicalPlan:

  trait Relation extends LogicalPlan
  trait UnaryRelation extends LogicalPlan:
    def input: Relation

  case class FLOWRQuery(
      forItems: Seq[ForItem],
      whereClause: Option[Where] = None,
      returnClause: Option[Return] = None
  )(override val nodeLocation: Option[NodeLocation] = None)
      extends Relation {}

  case class ForItem(id: String, in: Expression)(nodeLocation: Option[NodeLocation]) extends Expression

  case class Where(filterExpr: Expression)(nodeLocation: Option[NodeLocation])

  case class Return(exprs: Seq[Expression])(nodeLocation: Option[NodeLocation])
