package wvlet.ui

import org.scalajs.dom
import org.scalajs.dom.document
import play.api.libs.json._
import wvlet.log.LogFormatter.SourceCodeLogFormatter
import wvlet.log.LogTimestampFormatter.formatTimestamp
import wvlet.ui.component.{LayoutFrame, Navbar}
import wvlet.log._
import wvlet.ui.view.html.navbar

import scala.scalajs.js
import scalatags.JsDom.all._

object UILogFormatter extends LogFormatter {
  override def formatLog(r: LogRecord): String = {
    val loc =
      r.source
      .map(source => s" - (${source.fileLoc})")
      .getOrElse("")

    val log = f"${formatTimestamp(r.getMillis)} ${r.level.name}%5s [${r.leafLoggerName}] ${r.getMessage} ${loc}"
    r.cause match {
      case Some(ex) => s"${log}\n${LogFormatter.formatStacktrace(ex)}"
      case None => log
    }
  }
}

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

  def main() = {
    Logger.setDefaultLogLevel(LogLevel.DEBUG)
    info("Start WvletUI")
    val m = document.getElementById("main")
    val layout = LayoutFrame.render(<p>hello wvlet</p>)
    m.appendChild(layout)
  }
}
