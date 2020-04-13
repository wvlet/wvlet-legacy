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
package wvlet.dataflow.ui.component.editor

import scalajs.js
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLElement
import wvlet.airframe.http.rx.html.RxElement
import wvlet.dataflow.ui.component.editor.importedjs.monaco.editor.{
  Editor,
  IEditorConstructionOptions,
  IEditorMinimapOptions,
  IModelDecorationsChangedEvent,
  IStandaloneEditorConstructionOptions,
  ITextModel
}
import wvlet.dataflow.ui.component.editor.importedjs._
import wvlet.dataflow.ui.component.editor.importedjs.monaco.{IKeyboardEvent, KeyCode}
import wvlet.log.LogSupport
import wvlet.airframe.http.rx.html.all._
import wvlet.dataflow.ui.component.editor.importedjs.monaco.editor.EditorLayoutInfo

class TextEditor(initialValue: String = "", onEnter: String => Unit = { x: String =>
  }) extends RxElement
    with LogSupport {
  private val editorNode = {
    val editorNode: HTMLElement = document.createElement("div").asInstanceOf[HTMLElement]
    editorNode
  }

  private val editor = {
    val option = new scalajs.js.Object().asInstanceOf[IStandaloneEditorConstructionOptions]
    option.value = initialValue
    option.language = "sql"
    option.theme = "vs-dark"
    option.scrollBeyondLastLine = false
    option.automaticLayout = true
    option.fontFamily = "Menlo, Monaco, Consolas, Liberation Mono, Courier New, monospace"
    //option.fontSize = 14
    val minimapOptions = new js.Object().asInstanceOf[IEditorMinimapOptions]
    minimapOptions.enabled = false
    option.minimap = minimapOptions

    val editor = Editor.create(editorNode, option)
    editor.onKeyDown { e: IKeyboardEvent =>
      if (e.keyCode == KeyCode.Enter && e.metaKey) {
        val text = editor.getValue()
        info(s"Command + Enter is pressed:\n[content]\n${text}")
        onEnter(text)
        e.stopPropagation()
      }
    }
    editor
  }

  def getTextValue: String = {
    editor.getValue()
  }

  def updateLayout: Unit = {
    val lineHeight    = 18 // editor.editorOptions.lineHeight
    val lineCount     = editor.getModel().asInstanceOf[ITextModel].getLineCount().max(1)
    val topLineNumber = editor.getTopForLineNumber(lineCount + 1)
    val height        = topLineNumber.max(lineHeight * lineCount)

    //info(s"${lineHeight}, ${lineCount}, ${topLineNumber}, ${height}")
    editor.getDomNode() match {
      case el: HTMLElement =>
        el.parentElement.style.height = s"${height}px"

        editor.layout()
      case _ =>
    }
  }

  override def render: RxElement = {
    editor.onDidChangeModelDecorations { e: IModelDecorationsChangedEvent =>
      updateLayout
    }
    editor.onDidLayoutChange { e: EditorLayoutInfo =>
      //updateLayout
    }
    updateLayout

    div(
      cls -> "pl-0 mx-auto",
      editorNode
    )
  }
}
