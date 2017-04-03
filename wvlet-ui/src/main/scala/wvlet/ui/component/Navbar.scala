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
      <i class="fa ${iconName} fa-lg" width="20" height="20"/>

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

  def body =
    <nav class="col-2 navbar-inverse bg-inverse sidebar">
      <div class="nav navbar-nav">
        {state.map(page =>
        for (l <- links) yield {
          val isActive = page == l.name
          <a class={linkStyle(isActive)} href={l.url} onclick={(e: dom.MouseEvent) =>
            e.preventDefault()
            debug(s"clicked ${l.name}")
            state.update(x => l.name)}>
            {icon(l.icon)}{l.name}
          </a>
        }
      )}
      </div>
    </nav>

}

