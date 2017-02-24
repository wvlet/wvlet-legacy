package wvlet.ui

import scala.scalajs.js
import org.scalajs.dom
import dom.document
import scalatags.JsDom.all._

object WvletUI extends js.JSApp {
  def main() = {
    val m = document.getElementById("main")
    val message = p("hello world!!!")
    m.appendChild(message.render)
  }
}
