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

/**
  *
  */
object Navbar {

  @dom
  def navLink(url: String, name: String, iconName: String) = {
    <li class="nav-item">
      <a class="nav-link" href={ url }>
        <i class="material-icons">
          {iconName}
        </i>{name}
      </a>
    </li>
  }

  @dom
  def render = {
    <div class="container-fluid">
      <div class="row">
        <nav class="col-sm-3 col-md-2 sidebar">
          <span class="mdl-layout-title">wvlet</span>
          <ul class="nav nav-pills flex-column">
            { navLink("", "Home", "home").bind }
            { navLink("", "List", "list").bind }
            { navLink("", "Settings", "settings").bind }
          </ul>
        </nav>
      </div>
    </div>
  }


}
