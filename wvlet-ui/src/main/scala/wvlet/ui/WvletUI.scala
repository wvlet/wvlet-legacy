package wvlet.ui

import org.scalajs.dom
import org.scalajs.dom.document
import play.api.libs.json._
import wvlet.ui.component.{LayoutFrame, Navbar}
import wvlet.log._
import wvlet.ui.view.html.navbar

import scala.scalajs.js
import scalatags.JsDom.all._

object WvletUI extends js.JSApp with LogSupport {

  case class FormData(
    status: String,
    schema: Seq[String],
    data: Seq[JsArray]
  )
  implicit val formReader  = Json.reads[FormData]

  case class Project(id:Int, name:String)
  implicit val projectReader = Json.reads[Project]

  Logger.setDefaultHandler(new JSConsoleLogHandler)
  Logger.setDefaultLogLevel(LogLevel.ALL)

  def main() = {

    info("info")
    debug("debug")
    trace("trace")
    warn("warn")
    error("error")


    info("Start WvletUI")
    val m = document.getElementById("main")
    val layout = LayoutFrame.render(<p>hello wvlet</p>)
    m.appendChild(layout)
  }
}
