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
package wvlet.dataflow.server.api

import wvlet.airframe.http.{Endpoint, StaticContent}

case class ServerConfig()

/**
  *
  */
class ServerApi(config: ServerConfig) {
  private val staticContent = StaticContent
    .fromDirectory("../wvlet-ui/src/main/public")
    // TODO Need a more reliable way to select Scala version
    .fromDirectory("../wvlet-ui/target/scala-2.12")
    .fromDirectory("../wvlet-ui/target/scala-2.12/scalajs-bundler/main")

  @Endpoint(path = "/ui/*path")
  def ui(path: String) = {
    staticContent(if (path.isEmpty) "index.html" else path)
  }
}
