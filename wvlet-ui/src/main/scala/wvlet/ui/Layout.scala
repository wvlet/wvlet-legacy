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
package wvlet.ui
import scalatags.JsDom.all._

/**
  *
  */
object Layout {

  def navLink(url:String, name:String) = a(cls:="mdl-navigation__link", href:=url)(name)

  def layout(title:String) = {
    div(cls:="mdl-layout mdl-js-layout mdl-layout--fixed-header")(
      header(cls:="mdl-layout__header")(
        div(cls:="mdl-layout__header-row")(
          span(cls:="mld-layout-title")(title),
          div(cls:="mdl-layout-spacer"),
          tag("nav")(cls:="mdl-navigation mdl-layout--lage-screen-only")(
            navLink("", "Home"),
            navLink("", "Link")
          )
        )
      ),
      div(cls:="mdl-layout__drawer")(
        span(cls:="mdl-layout-title")(title),
        tag("nav")(cls:="mdl-navigation")(
          navLink("", "Home"),
          navLink("", "Link")
        )
      ),
      tag("main")(cls:="mdl-layout__content")(
        div(cls:="page-content")(
          div(id:="main")
        )
      )
    )
  }

}
