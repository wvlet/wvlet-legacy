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
package wvlet.workflow.macros

/**
 * This class is used for wrapping a code block since case class does not allow having
 * a code block as a named parameter.
 *
 * @param body
 */
class CodeBlock[Context](body: Context => Unit, lazyF0: Option[LazyF0[Unit]] = None) {
  def this(body: Context => Unit) = this(body, None)

  def this(body: => Unit) = this({ context : Context => body }, Some(LazyF0[Unit](body)))

  def runSolely(context:Context) = body(context)

  def codeBlockClass: Class[_] = lazyF0.map(_.functionClass).getOrElse(body.getClass)

  def codeBlockInstance: AnyRef = lazyF0.map(_.functionInstance).getOrElse(body)
}
