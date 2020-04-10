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
package wvlet.dataflow.api.v1.editor

import java.util.UUID

import wvlet.airframe.http.RPC

@RPC
trait EditorApi {
  def listProjects: Seq[Project]
  def addProject(request: CreateProjectRequest): Project
  def listFiles(project: Project): ListFilesResponse
}

case class Project(id: String, name: String)
case class CreateProjectRequest(name: String, requestId: UUID = UUID.randomUUID())
case class ListFilesResponse(project: Project)
