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
package wvlet.lang.parser
import org.antlr.v4.runtime.tree.{ErrorNode, ParseTree, RuleNode, TerminalNode}
import org.antlr.v4.runtime.{ParserRuleContext, Token}
import wvlet.lang.model.*
import wvlet.lang.parser.WvletLangParser.{MultiLineForContext, SingleLineForContext}
import wvlet.log.LogSupport

import scala.jdk.CollectionConverters.*

class WvletInterpreter extends WvletLangVisitor[Any] with LogSupport {

  private def syntaxError(msg: String, ctx: ParserRuleContext): Nothing = {
    val loc = getLocation(ctx)
    throw new IllegalArgumentException(s"${loc.getOrElse("")}: ${msg}")
  }

  private def getLocation(token: Token): Option[NodeLocation] = {
    Some(NodeLocation(token.getLine, token.getCharPositionInLine + 1))
  }

  private def getLocation(ctx: ParserRuleContext): Option[NodeLocation] = getLocation(ctx.getStart)

  private def getLocation(node: TerminalNode): Option[NodeLocation] = getLocation(node.getSymbol)

  private def expression(ctx: ParserRuleContext): Expression = {
    ctx.accept(this) match {
      case e: Expression => e
      case other =>
        Identifier(ctx.getText)
      // syntaxError(s"Expected an expression but found ${other}", ctx)
    }
  }

  override def visitSingleStatement(ctx: WvletLangParser.SingleStatementContext): Any = ???

  override def visitStatement(ctx: WvletLangParser.StatementContext): LogicalPlan = {
    visitQuery(ctx.query())
  }

  override def visitQuery(ctx: WvletLangParser.QueryContext): Relation = {
    val forClause = visitForClause(ctx.forClause())
    FlowerQuery(
      forClause = forClause
    )(getLocation(ctx))
  }

  override def visitForClause(ctx: WvletLangParser.ForClauseContext): ForClause = {
    val forItems = ctx match {
      case m: MultiLineForContext =>
        m.forItem().asScala.map(x => visitForItem(x)).toSeq
      case s: SingleLineForContext =>
        s.forItem().asScala.map(x => visitForItem(x)).toSeq
      case _ =>
        throw syntaxError(s"Unknown for clause: ${ctx.getClass}", ctx)
    }
    ForClause(forItems)
  }

  override def visitForItem(ctx: WvletLangParser.ForItemContext): ForItem = {
    ForItem(ctx.identifier().getText, expression(ctx.expression()))(getLocation(ctx))
  }

  override def visitQualifiedName(ctx: WvletLangParser.QualifiedNameContext): Any = ???

  override def visitExpression(ctx: WvletLangParser.ExpressionContext): Expression = {
    val qname = ctx.qualifiedName()
    info(qname)
    Identifier(qname.getText)
  }

  override def visitFunctionCall(ctx: WvletLangParser.FunctionCallContext): Any = ???

  override def visitFunctionArg(ctx: WvletLangParser.FunctionArgContext): Any = ???

  override def visitNamedExpression(ctx: WvletLangParser.NamedExpressionContext): Any = ???

  override def visitNonReserved(ctx: WvletLangParser.NonReservedContext): Any = ???

  override def visitUnquotedIdentifier(ctx: WvletLangParser.UnquotedIdentifierContext): Any = ???

  override def visitQuotedIdentifier(ctx: WvletLangParser.QuotedIdentifierContext): Any = ???

  override def visit(tree: ParseTree): Any = ???

  override def visitChildren(node: RuleNode): Any = {}

  override def visitTerminal(node: TerminalNode): Any = ???

  override def visitErrorNode(node: ErrorNode): Any = ???

  override def visitReturnClause(ctx: WvletLangParser.ReturnClauseContext): Any = {}

  override def visitSingleLineExpr(ctx: WvletLangParser.SingleLineExprContext): Any = ???

  override def visitMultiLineExpr(ctx: WvletLangParser.MultiLineExprContext): Any = ???
}