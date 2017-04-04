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

/**
  *
  */
object SearchBar {
  def bodyMDL =
    <div class="mdl-textfield mdl-js-textfield">
      <input class="mdl-textfield__input" type="text" id="sample1"/>
      <label class="mdl-textfield__label" for="sample1">Search...</label>
    </div>

  def bodyBS4 =
    <div class="bd-search">
      <input type="email" class="form-control" placeholder="Search..."/>
    </div>

  def body =
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right">
      <label class="mdl-button mdl-js-button mdl-button--icon" for="fixed-header-drawer-exp">
        <i class="material-icons">search</i>
      </label>
      <div class="mdl-textfield__expandable-holder">
        <input class="mdl-textfield__input" type="text" name="sample" id="fixed-header-drawer-exp"/>
      </div>
    </div>
}
