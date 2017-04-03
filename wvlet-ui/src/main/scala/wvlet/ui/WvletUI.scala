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
    Logger.setDefaultLogLevel(LogLevel.TRACE)

    info("info log")
    debug("debug log")
    trace("trace log")
    warn("warn log")
    error("error log")
    //val body = document.getElementById("body")

    val m = document.getElementById("main")
    val layout = LayoutFrame.render(<p>hello wvlet</p>)
    m.appendChild(layout)

    //info("appended child")
    //body.appendChild(Layout.layout("wvlet").render)
    //val content = navbar.render()
    //content.body
    //m.innerHTML = content.toString
    //m.appendChild(pre(content.toString).render)
    //m.appendChild(Navbar.render.render)
//    val url = "http://localhost:8080/v1/project"
//    Ajax.get(url).map {xhr =>
//      if (xhr.status == 200) {
//        val json = StaticBinding.parseJsValue(xhr.responseText)
//        json.validate[Seq[Project]] match {
//          case s: JsSuccess[Seq[Project]] =>
//            val rows = s.get.map(x => Seq(x.id, x.name))
//            val content = Layout.dataTable(Seq("id", "name"), rows)
//            m.appendChild(content.render)
//          case e: JsError =>
//            m.appendChild(p(e.errors.mkString("\n")).render)
//        }
//      }
//      else {
//        m.appendChild(p(xhr.responseText).render)
//      }
//
//    }
  }
}
