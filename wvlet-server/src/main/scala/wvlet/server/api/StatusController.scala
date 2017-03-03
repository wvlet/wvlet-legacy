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

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.Duration

case class Status(message: String)

class StatusController extends Controller {
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
