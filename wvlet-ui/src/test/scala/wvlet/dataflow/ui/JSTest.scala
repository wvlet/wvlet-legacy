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
package wvlet.dataflow.ui

import wvlet.airframe.rx.html.*
import wvlet.airframe.rx.html.all.*
import wvlet.airspec.AirSpec
import org.scalajs.dom.document
import wvlet.dataflow.ui.component.MainPanel

class JSTest extends AirSpec {
  test("sample") {
    val newDiv = document.createElement("div")

  }

  test("rx element test") {
    val rx = new RxElement() {
      override def render: RxElement = div("hello dom")
    }
    DOMRenderer.render(rx)
  }

  test("render monaco") {
    DOMRenderer.render(MainPanel())
  }
}
