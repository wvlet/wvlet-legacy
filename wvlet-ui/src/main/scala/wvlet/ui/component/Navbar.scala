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

import com.thoughtworks.binding.dom
import scalatags.JsDom.all._

/**
  *
  */
object Navbar {
  def icon(iconName:String) = {
    i(cls := "material-icons", role := "presentation")(iconName)
  }

  def navLink(url:String, name:String, iconName:String) = {
    li(cls:="nav-item")(
      a(cls := "nav-link", href := url)(
        icon(iconName),
        name
      )
    )
  }


  def render = {
    tag("nav")(cls:="col-sm-3 col-md-2 sidebar")(
      span(cls:="mdl-layout-title")("wvlet"),
      ul(cls:="nav nav-pills flex-column")(
        navLink("", "Home", "home"),
        navLink("", "List", "list"),
        navLink("", "Settings", "settings")
      )
    )
  }

}
