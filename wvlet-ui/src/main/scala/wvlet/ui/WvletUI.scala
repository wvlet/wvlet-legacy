package wvlet.ui

import scala.scalajs.js
import org.scalajs.dom
import dom.document
import org.scalajs.jquery.jQuery
import scalatags.JsDom.all._

object WvletUI extends js.JSApp {
  def main() = {
    val d = js.Dynamic.global.document
    val m = d.getElementById("main")
    //m.appendChild(p("hello world!"))
    //val p = d.createElement("p")
    //p.innerHTML = "Hello world!"
    //m.appendChild(p)
    jQuery("#main").append("<p>Hello wvlet</p>")
  }
}
