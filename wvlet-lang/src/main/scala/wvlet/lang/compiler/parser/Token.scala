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

enum TokenType:
  case Control, Literal, Identifier, Op, Keyword

import TokenType.*

enum Token(val tokenType: TokenType, val str: String):
  // special tokens
  case EMPTY   extends Token(Control, "<empty>")
  case ERROR   extends Token(Control, "<erroneous token>")
  case EOF     extends Token(Control, "<eof>")
  case NEWLINE extends Token(Control, "<newline>")

  // Literals
  case INTEGER_LITERAL extends Token(Literal, "<integer literal>")
  case DECIMAL_LITERAL extends Token(Literal, "<decimal literal>")
  case EXP_LITERAL     extends Token(Literal, "<exp literal>")
  case LONG_LITERAL    extends Token(Literal, "<long literal>")
  case FLOAT_LITERAL   extends Token(Literal, "<float literal>")
  case DOUBLE_LITERAL  extends Token(Literal, "<double literal>")
  case STRING_LITERAL  extends Token(Literal, "<string literal>")
  case STRING_PART     extends Token(Literal, "<string part>")

  // Identifiers
  case IDENTIFIER extends Token(Identifier, "<identifier>")
  // Identifier wrapped in backquotes `....`
  case BACKQUOTED_IDENTIFIER extends Token(Identifier, "<quoted identifier>")

  // Parentheses
  case L_PAREN   extends Token(Op, "(")
  case R_PAREN   extends Token(Op, ")")
  case L_BRACE   extends Token(Op, "{")
  case R_BRACE   extends Token(Op, "}")
  case L_BRACKET extends Token(Op, "[")
  case R_BRACKET extends Token(Op, "]")
  case INDENT    extends Token(Control, "<indent>")
  case OUTDENT   extends Token(Control, "<outdent>")

  // Special symbols
  case COLON extends Token(Op, ":")
  case COMMA extends Token(Op, ",")
  case DOT   extends Token(Op, ".")

  case SINGLE_QUOTE extends Token(Op, "'")
  case DOUBLE_QUOTE extends Token(Op, "\"")

  // Special keywords
  case EQ   extends Token(Op, "=")
  case NEQ  extends Token(Op, "!=")
  case LT   extends Token(Op, "<")
  case GT   extends Token(Op, ">")
  case LTEQ extends Token(Op, "<=")
  case GTEQ extends Token(Op, ">=")

  // literal keywords
  case NULL  extends Token(Keyword, "null")
  case TRUE  extends Token(Keyword, "true")
  case FALSE extends Token(Keyword, "false")

  // Alphabectic keywords
  case IN     extends Token(Keyword, "in")
  case DEF    extends Token(Keyword, "def")
  case SCHEMA extends Token(Keyword, "schema")
  case WITH   extends Token(Keyword, "with")

  case SELECT   extends Token(Keyword, "select")
  case FOR      extends Token(Keyword, "for")
  case LET      extends Token(Keyword, "let")
  case WHERE    extends Token(Keyword, "where")
  case GROUP_BY extends Token(Keyword, "group by")
  case HAVING   extends Token(Keyword, "having")
  case RETURN   extends Token(Keyword, "return")
  case ORDER_BY extends Token(Keyword, "order by")

  case RUN    extends Token(Keyword, "run")
  case IMPORT extends Token(Keyword, "import")
  case EXPORT extends Token(Keyword, "export")

  case IF   extends Token(Keyword, "if")
  case THEN extends Token(Keyword, "then")
  case ELSE extends Token(Keyword, "else")

object Tokens:
  import Token.*
  val keywords       = Token.values.filter(_.tokenType == Keyword).toSeq
  val specialSymbols = Token.values.filter(_.tokenType == Op).toSeq

  val allKeywords = keywords ++ specialSymbols

  val keywordTable = allKeywords.map(x => x.str -> x).toMap
