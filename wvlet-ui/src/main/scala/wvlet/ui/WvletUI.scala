package wvlet.ui

import scala.scalajs.js
import org.scalajs.dom
import dom.{Event, document}
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.raw.XMLHttpRequest

import scala.util.{Failure, Success}
import scalatags.JsDom.all._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

object WvletUI extends js.JSApp {

  def main() = {
    val body = document.getElementById("body")
    body.appendChild(Layout.layout("wvlet").render)

    val m = document.getElementById("main")

    val url = "https://gist.githubusercontent.com/xerial/d953d6e301c7d08c064edc3cf8312f1c/raw/281c44e5830b52456ec09a82fe60848fec0290ff/sample.json"

    Ajax.get(url).map {xhr =>
      if (xhr.status == 200) {
        val json = js.JSON.parse(xhr.responseText)
        val header = json.schema.asInstanceOf[js.Array[String]].toSeq
        val content = Layout.dataTable(header)
        m.appendChild(content.render)
      }
      else {
        m.appendChild(p(xhr.responseText).render)
      }

      
    }
  }
}
