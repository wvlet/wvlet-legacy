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
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.util.{Await, Duration}

/**
  *
  */
class WvletApi extends HttpServer {

  override val defaultFinatraHttpPort: String = ":8080"

  override def configureHttp(router: HttpRouter) {
    router
    .filter[CommonFilters]
    .add[StatusApiController]
    .add[ProjectApiController]
  }
}

case class Status(message: String)

class StatusApiController extends Controller {
  get("/v1/status") {request: Request =>
    response.ok(Status("Ok!"))
  }

  post("/v1/shutdown") {request: Request =>
    try {
      response.ok(Status("Shutting down"))
    }
    finally {
      WvletServer.close(Duration.fromSeconds(3))
    }
  }
}

case class Project(id: Int, name: String)
case class AddProject(
  name: String,
  description: Option[String] = None,
  classpath: Option[String] = None
)

class ProjectApiController extends Controller {

  get("/v1/project") {request: Request =>
    response.ok(Project(1, "sample project"))
  }

  post("/v1/project") {p: AddProject =>
    response.ok(Project(1, "hello"))
  }

}