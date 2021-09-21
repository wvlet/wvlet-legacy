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

import TaskRef._
import wvlet.airframe.ulid.ULID

import java.time.Instant

case class TaskRequest(
    parentId: Option[TaskId],
    taskBody: TaskBody,
    tags: Tags = Tags.empty,
    trigger: TaskTrigger = TaskTrigger.ON_SUCCESS,
    idempotentKey: ULID = ULID.newULID
)

/**
  */
case class TaskRef(
    parentId: Option[TaskId],
    id: TaskId,
    status: TaskStatus,
    tags: Tags,
    createdAt: Instant,
    updatedAt: Instant,
    completedAt: Option[Instant] = None
)

object TaskRef {
  type TaskId   = String
  type TaskBody = Map[String, Any]

  object TaskBody {
    val empty: TaskBody = Map.empty
  }

  type Tags = Seq[String]

  object Tags {
    val empty: Tags = Seq.empty
  }

  sealed trait TaskTrigger
  object TaskTrigger {
    case object ON_SUCCESS extends TaskTrigger
    case object ON_FAILURE extends TaskTrigger

    def unapply(s: String): Option[TaskTrigger] = Seq(ON_SUCCESS, ON_FAILURE).find(_.toString == s)
  }

}

case class TaskList(
    timestamp: Instant,
    tasks: Seq[TaskRef]
)
