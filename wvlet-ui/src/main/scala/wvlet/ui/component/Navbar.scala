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
import rx._

import scalatags.JsDom.all._

/**
  *
  */
object Navbar extends RxElement {

  override val state = Var("Home")

  def icon(iconName: String) = {
    i(cls:="material-icons d-inline-block align-bottom", width:=20, height:=20)(iconName)
  }

  case class NavLink(url: String, name: String, icon: String)

  val links = Seq(
    NavLink("#", "Home", "home"),
    NavLink("#", "List", "list"),
    NavLink("#", "Settings", "settings")
  )

  private def linkStyle(isActive: Boolean) = {
    if (isActive) {
      "nav-item nav-link active"
    }
    else {
      "nav-item nav-link"
    }
  }

  def draw = {
    val page = state.now
    tag("nav")(cls := "navbar navbar-toggleable-md navbar-fixed-top navbar-inverse bg-inverse")(
      a(cls := "navbar-brand", href := "#")("wvlet"),
      div(cls:="nav-collapse", id:="navbarNavAltMarkup")(
        div(cls := "navbar-nav")(
          for (l <- links) yield {
            val isActive = page == l.name
            val anchor = a(cls := linkStyle(isActive), href := l.url)(
              //icon(l.icon),
              s" ${l.name} "
            ).render
            anchor.onclick = (e: dom.MouseEvent) => {
              e.preventDefault()
              state.update(l.name)
            }
            anchor
          }
        )
      )
    ).render
  }
}

