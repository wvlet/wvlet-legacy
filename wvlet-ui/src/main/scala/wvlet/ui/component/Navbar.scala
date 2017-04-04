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

import mhtml._
import org.scalajs.dom
import wvlet.log.LogSupport

/**
  *
  */
object Navbar extends RxElement with LogSupport {
  private val state: Var[String] = Var("Home")

  def icon(iconName: String) =
      <i class={s"fa ${iconName} fa-lg"} width="20" height="20"/>

  case class NavLink(url: String, name: String, icon: String)

  val links = Seq(
    NavLink("#", "Home", "fa-home"),
    NavLink("#", "List", "fa-list"),
    NavLink("#", "Settings", "fa-cog")
  )

  private def linkStyle(isActive: Boolean) = {
    if (isActive) {
      "nav-item nav-link active"
    }
    else {
      "nav-item nav-link"
    }
  }

  private def onClick(name: String) = { e: dom.MouseEvent =>
    e.preventDefault()
    debug(s"clicked ${name}")
    state.update(x => name)
  }

  def body = state.map {page =>
    <div class="mdl-layout__drawer">
      <span class="mdl-layout-title">wvlet</span>
      <nav class="mdl-navigation">
        {
        links.map{l =>
          val isActive = page == l.name
          <a class={s"mdl-navigation__link${if(isActive) " active" else ""}"} href={s"${l.url}"} onclick={onClick(l.name)}>
            {icon(l.icon)} {l.name}
          </a>
        }
        }
      </nav>
    </div>
  }

}
