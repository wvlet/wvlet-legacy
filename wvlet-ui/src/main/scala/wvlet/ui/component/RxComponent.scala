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
package wvlet.ui.component

import org.scalajs.dom
import rx.{Ctx, Rx, Var}

import scalatags.JsDom.TypedTag

/**
  *
  */
trait RxElement {

  val state : Rx[_] = Var()
  private var lastElement : dom.Element = null

  protected def draw : dom.Element

  def render : dom.Element = {
    import Ctx.Owner.Unsafe._
    lastElement = draw
    state.foreach { s =>
      val newElement = draw
      if(lastElement.parentNode != null) {
        lastElement.parentNode.replaceChild(newElement, lastElement)
      }
      lastElement = newElement
    }
    lastElement
  }
}

trait RxComponent {

  val state : Rx[_] = Var()

  private var lastElement : dom.Element = null

  protected def draw[T <: TypedTag[_]](body:T) : dom.Element

  def render[T <: TypedTag[_]](body:T) : dom.Element = {
    import Ctx.Owner.Unsafe._
    lastElement = draw(body)
    state.foreach { s =>
      val newElement = draw(body)
      if(lastElement.parentNode != null) {
        lastElement.parentNode.replaceChild(newElement, lastElement)
      }
      lastElement = newElement
    }
    lastElement
  }

}
