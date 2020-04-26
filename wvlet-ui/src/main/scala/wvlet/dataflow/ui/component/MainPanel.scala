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
package wvlet.dataflow.ui.component

import wvlet.airframe.http.rx.html.RxElement
import wvlet.airframe.http.rx.html.all._
import wvlet.airframe._
import wvlet.airframe.http.rx.Rx
import wvlet.dataflow.api.v1.ServiceApi.ServiceInfo
import wvlet.dataflow.ui.component.editor.TextEditor

/**
  *
  */
trait MainPanel extends RxElement with RPCService {

  private val sideNav = bind[SideNav]
  private val header  = bind[Header]

  private val serviceInfo = Rx.variable[Option[ServiceInfo]](None)

  rpc.serviceApi.serviceInfo().map { x =>
    serviceInfo := Some(x)
  }

  override def render: RxElement = {
    div(
      cls -> "container-fluid p-0",
      div(
        cls -> "d-flex",
        div(cls -> "p-0", sideNav),
        div(
          cls -> "flex-fill p-0",
          div(cls -> "d-flex", div(cls -> "ml-auto", header)),
          div(
            cls -> "container-fluid",
            "Hello wvlet: ",
            serviceInfo.map { x =>
              x.map(_.version)
            },
            hr(),
            div(style -> "width: 95%;", new TextEditor("Hello Wvlet"))
          )
        )
      )
    )
  }
}

class Header extends RxElement {

  def icon(name: String, iconCls: String) = {
    li(cls -> "nav-item",
       a(
         cls   -> "nav-link px-2",
         style -> "text-decoration: none;",
         href  -> s"#${name}",
         i(cls -> iconCls, style -> "width: 20px;"),
         s" ${name}"
       ))
  }

  override def render: RxElement = {
    div(
      cls -> "sidebar-menu",
      div(
        ul(cls -> "nav",
           icon("Help", "fas fa-question-circle"),
           icon("Settings", "fas fa-cog"),
           icon("Login", "fas fa-user-circle"))
      )
    )
  }
}

class SideNav extends RxElement {

  def navItem(name: String, iconCls: String) = {
    ul(
      cls   -> "p-0 m-0",
      style -> "list-style-type: none;",
      li(
        cls   -> "nav-item",
        style -> "white-space: nowrap;",
        a(
          cls   -> "nav-link pl-2 pr-4",
          style -> "text-decoration: none; list-style-type: none; ",
          href  -> s"#${name}",
          i(cls -> iconCls, style -> "width: 20px;"),
          s" ${name}"
        )
      )
    )
  }

  override def render: RxElement = {
    nav(
      cls -> "d-block sidebar-menu",
      div(
        cls -> "sidebar-content",
        div(
          cls -> "d-flex justify-content-center",
          a(
            cls   -> "navbar-brand",
            style -> "text-decoration: inherit; color: inherit; ",
            href  -> "#",
            img(
              cls    -> "d-inline-block",
              src    -> "img/logo-40px.png",
              alt    -> "wvlet",
              width  -> 40,
              height -> 40
            )
          )
        ),
        div(
          navItem("Projects", "fas fa-th-list"),
          navItem("Databases", "fas fa-database"),
          navItem("Queries", "fas fa-binoculars"),
          navItem("Notebooks", "fas fa-book-open")
        ),
//        div(
//          cls   -> "sidebar-footer py-2",
//          style -> "position: absolute; bottom: 0px; display: flex;",
//          navItem("Settings", "fas fa-cog")
//        )
      )
    )
  }
}
