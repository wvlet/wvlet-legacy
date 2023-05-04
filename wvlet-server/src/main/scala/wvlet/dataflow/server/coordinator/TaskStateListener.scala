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
package wvlet.dataflow.server.coordinator

import wvlet.dataflow.api.v1.{TaskError, TaskRef}
import wvlet.log.LogSupport

/**
  */
trait TaskStateListener {
  def onStateChange(taskRef: TaskRef): Unit
}

object TaskStateListener extends LogSupport {
  def defaultListener: TaskStateListener = new TaskStateListener {
    override def onStateChange(taskRef: TaskRef): Unit = {
      taskRef.taskError match {
        case None =>
          if (!taskRef.isFailed) {
            info(taskRef)
          } else {
            warn(taskRef)
          }
        case Some(TaskError(_, _, None)) =>
          warn(taskRef)
        case Some(TaskError(_, _, Some(cause))) =>
          warn(taskRef, cause)
      }

    }
  }
}
