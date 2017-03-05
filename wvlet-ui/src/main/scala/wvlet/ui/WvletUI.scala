package wvlet.ui

import org.scalajs.dom
import org.scalajs.dom.document
import play.api.libs.json._
import wvlet.ui.component.{LayoutFrame, Navbar}
import wvlet.ui.view.html.navbar

import scala.scalajs.js
import scalatags.JsDom.all._

object WvletUI extends js.JSApp {

  case class FormData(
    status: String,
    schema: Seq[String],
    data: Seq[JsArray]
  )
  implicit val formReader  = Json.reads[FormData]

  case class Project(id:Int, name:String)
  implicit val projectReader = Json.reads[Project]

  def main() = {

    val body = document.getElementById("body")
    val m = document.getElementById("main")
    //body.appendChild(Layout.layout("wvlet").render)
    //val content = navbar.render()
    //content.body
    //m.innerHTML = content.toString
    //m.appendChild(pre(content.toString).render)

    val layout = LayoutFrame.render(
      p("hello")
    )
    m.appendChild(layout)

//
//
//
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
