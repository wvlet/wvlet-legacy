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
package wvlet.flow.api.v1

/**
  */
sealed trait TaskStatus {
  def isDone: Boolean
  def name: String = toString
}

object TaskStatus {

  /**
    * Task is queued at the coordinator
    */
  case object QUEUED extends TaskStatus {
    override def isDone: Boolean = false
  }

  /**
    * Task is starting at the coordinator
    */
  case object STARTING extends TaskStatus {
    override def isDone: Boolean = false
  }

  /**
    * Task is running at a worker
    */
  case object RUNNING extends TaskStatus {
    override def isDone: Boolean = false
  }
  case object FINISHED extends TaskStatus {
    override def isDone: Boolean = true
  }
  case object FAILED extends TaskStatus {
    override def isDone: Boolean = true
  }
  case object CANCELLED extends TaskStatus {
    override def isDone: Boolean = true
  }

  def all: Seq[TaskStatus] = Seq(QUEUED, STARTING, RUNNING, FINISHED, FAILED, CANCELLED)
  def unapply(s: String): Option[TaskStatus] = {
    all.find(_.name == s)
  }
}
