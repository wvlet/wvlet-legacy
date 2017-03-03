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

import javax.inject.Inject

import com.google.inject.Singleton
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

case class Project(id: Int, name: String)

case class AddProject(
  name: String,
  description: Option[String] = None,
  classpath: Option[String] = None
)

class ProjectController @Inject()(
  projectStore:ProjectStore
) extends Controller {
  get("/v1/project") {request: Request =>
    response.ok {
      projectStore.listProjects
    }
  }

  post("/v1/project") {p: AddProject =>
    val newProject = projectStore.addProject(p)
    response.ok(newProject)
  }
}

@Singleton
class ProjectStore {
  private var count = 1
  private var projects: Seq[Project] = Seq(Project(1, "sample"))

  def addProject(p: AddProject) {
    synchronized {
      projects :+= Project(count + 1, p.name)
      count += 1
    }
  }

  def listProjects = projects

}
