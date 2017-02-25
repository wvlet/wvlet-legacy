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

  def icon(iconName:String) = {
    i(cls := "mdl-color-text--blue-grey-400 material-icons", role := "presentation")(iconName)
  }

  def navLink(url:String, name:String, iconName:String) = {
    a(cls := "mdl-navigation__link", href := url)(
      icon(iconName),
      name
    )
  }

  def dataTable(tableHeader:Seq[String]) = {
    table(cls:="mdl-data-table mdl-js-data-table mdl-shadow--2dp")(
      thead(
        tr(
          for(h <- tableHeader) yield th(cls:="mdl-data-table__cell--non-numeric")(h)
        )
      )
    )
  }


  def layout(title:String) = {
    div(cls:="mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header has-drawer")(
      header(cls:="mdl-layout__header")(
        div(cls:="mdl-layout__header-row")(
          span(cls:="mld-layout-title")(title),
          div(cls:="mdl-layout-spacer"),
          icon("search")
//          tag("nav")(cls:="mdl-navigation mdl-layout--lage-screen-only")(
//            navLink("", "Home"),
//            navLink("", "Link")
//          )
        )
      ),
      div(cls:="mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50")(
        span(cls:="mdl-layout-title")(title),
        tag("nav")(cls:="wvlet-navigation mdl-navigation")(
          navLink("", "Home", "home"),
          navLink("list", "List", "list"),
          navLink("setting", "Settings", "settings")
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
