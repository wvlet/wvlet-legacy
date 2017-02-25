package wvlet.ui

import scala.scalajs.js
import org.scalajs.dom
import dom.document
import org.scalajs.dom.ext.Ajax

import scala.util.{Failure, Success}
import scalatags.JsDom.all._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

object WvletUI extends js.JSApp {
  def main() = {
    val body = document.getElementById("body")
    body.appendChild(Layout.layout("wvlet").render)

    val m = document.getElementById("main")

    val url = "https://gist.githubusercontent.com/xerial/d953d6e301c7d08c064edc3cf8312f1c/raw/b12b48eea186537c8297cf957feb6991903f07eb/sample.json"

    val future = Ajax.get(url)
    future.onComplete {
      case Success(xhr) =>
        val json = js.JSON.parse(xhr.responseText)
        val header = json.schema.asInstanceOf[js.Array[String]].toSeq
        val content = Layout.dataTable(header)
        m.appendChild(content.render)
      case Failure(e) =>
        m.appendChild(p(e.getMessage))
    }
  }
}
