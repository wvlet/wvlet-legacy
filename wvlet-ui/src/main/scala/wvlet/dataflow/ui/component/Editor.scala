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

import wvlet.airframe.rx.html.*
import all.*
import typings.monacoEditor.mod.editor
import org.scalajs.dom

class Editor extends RxElement:
  override def onMount: Unit =
    editor.create(
      dom.document.getElementById("editor").asInstanceOf[dom.HTMLElement],
      editor
        .IStandaloneEditorConstructionOptions()
        .setValue(s"SELECT 1")
        .setLanguage("sql")
        .setTheme("vs-dark")
    )

  override def render: RxElement = div(
    id    -> "editor",
    style -> "width: 800px; height: 250px; border: 1px solid grey;"
  )
