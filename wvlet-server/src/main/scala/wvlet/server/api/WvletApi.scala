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
package wvlet.server.api

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.{Await, Duration}
import io.finch.{Endpoint, Ok, get}
import io.finch._
import io.finch.Application.Json
import io.finch.playjson._
import play.api.libs.json._
import wvlet.log.LogSupport
import wvlet.server.api.WvletApi.Status


/**
  *
  */
object WvletApi extends StatusApi with ProjectApi with LogSupport
{
  case class Status(message: String)

  implicit val statusWriter  = Json.writes[Status]

  lazy val service: Service[Request, Response] =
    (statusService :+: projectService)
    .toServiceAs[Json]

}

trait StatusApi extends LogSupport {
  lazy val statusService = status :+: shutdown

  def status: Endpoint[Status] = get("v1" :: "status") {
    Ok(Status("Ok!"))
  }

  def shutdown: Endpoint[Status] = get("v1" :: "shutdown") {
    try {
      Ok(Status("Shutting down"))
    }
    finally {
      WvletServer.server.close(Duration.fromSeconds(3))
    }
  }
}

trait ProjectApi extends LogSupport {
  case class Project(id: Int, name: String)
  case class AddProject(
    name:String,
    description:Option[String] = None,
    classpath:Option[String] = None
  )

  implicit val projectWriter = Json.writes[Project]
  implicit val projectReader = Json.reads[AddProject]

  lazy val projectService = project :+: addProject
  private def projectPath = "v1" :: "project"

  def project: Endpoint[Project] = get(projectPath) {
    try {
      Ok(Project(1, "sample project"))
    }
    catch {
      case e: Throwable =>
        error(e)
        throw e
    }
  }

  def addProject: Endpoint[Project] = post(projectPath :: jsonBody[AddProject]) { p: AddProject =>
    info(s"Add project ${p}")
    Ok(Project(1, "hello"))
  }

}