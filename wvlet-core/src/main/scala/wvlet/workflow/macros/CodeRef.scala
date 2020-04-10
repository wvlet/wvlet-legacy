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

import java.util.concurrent.atomic.AtomicInteger
import scala.language.existentials
import scala.reflect.macros.blackbox.Context

object CodeRef {
  class MacroHelper[C <: Context](val c: C) {

    import c.universe._

    /**
      * Find a function/variable/class context where the expression is used
      * @return
      */
    def createRef: c.Expr[CodeRef] = {
      // Find the enclosing method.
      val owner = c.internal.enclosingOwner
      val name = if (owner.fullName.endsWith("$anonfun")) {
        owner.fullName.replaceAll("\\$anonfun$", "") + getNewName()
      } else {
        owner.fullName
      }

      val selfCl = c.Expr[AnyRef](This(typeNames.EMPTY))
      val pos    = c.enclosingPosition
      c.Expr[CodeRef](
        q"wvlet.workflow.macros.CodeRef($selfCl.getClass, ${name}, ${pos.source.path}, ${pos.line}, ${pos.column})"
      )
    }
  }

  private val counter      = new AtomicInteger(0)
  def getNewName(): String = s"t${counter.getAndIncrement()}"

  def ref(c: Context) = new MacroHelper[c.type](c).createRef
}

/**
  *
  */
case class CodeRef(owner: Class[_], name: String, sourcePath: String, line: Int, column: Int) {
  def baseTrait: Class[_] = {

    // If the class name contains $anonfun, it is a compiler generated class.
    // If it contains $anon, it is a mixed-in trait
    val isAnonFun = owner.getSimpleName.contains("$anon")
    if (!isAnonFun) {
      owner
    } else {
      // If the owner is a mix-in class
      owner.getInterfaces.headOption
        .orElse(Option(owner.getSuperclass))
        .getOrElse(owner)
    }
  }

  private def format(op: Option[String]): String = op.map(_.toString).getOrElse("")

  def fullName = {
    val className = baseTrait.getSimpleName.replaceAll("\\$", "")
    s"${name}"
  }

  def shortName = {
    Option(name.split("\\.")).map(a => a(a.length - 1)).getOrElse(name)
  }

  override def toString = {
    s"${shortName} [L$line:$column]"
  }
}
