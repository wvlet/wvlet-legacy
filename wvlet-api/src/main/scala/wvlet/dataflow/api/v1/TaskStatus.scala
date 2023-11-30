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
package wvlet.dataflow.api.v1

/**
  */
enum TaskStatus(val isDone: Boolean):
  def name: String = this.toString

  /**
    * Task is queued at the coordinator
    */
  case QUEUED extends TaskStatus(isDone = false)

  /**
    * Task is starting at the coordinator
    */
  case STARTING extends TaskStatus(isDone = false)

  /**
    * Task is running at a worker
    */
  case RUNNING   extends TaskStatus(isDone = false)
  case FINISHED  extends TaskStatus(isDone = true)
  case FAILED    extends TaskStatus(isDone = true)
  case CANCELLED extends TaskStatus(isDone = true)

  def all: Seq[TaskStatus] = TaskStatus.values

  def unapply(s: String): Option[TaskStatus] =
    all.find(_.name == s)
