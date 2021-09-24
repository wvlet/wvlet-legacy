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
package wvlet.dataflow.server.util

import wvlet.dataflow.api.v1.TaskApi.TaskId
import wvlet.dataflow.api.v1.{TaskRef, TaskStatus}
import wvlet.dataflow.server.ServerModule.ApiClient

import scala.annotation.tailrec

object TaskUtil {

  def waitCompletion(client: ApiClient, taskId: TaskId, maxRetry: Int = 30): TaskRef = {
    @tailrec
    def loop(retryCount: Int): TaskRef = {
      if (retryCount >= maxRetry) {
        throw new IllegalStateException(s"Task: ${taskId} didn't finish in time")
      } else {
        client.TaskApi.getTask(taskId) match {
          case None =>
            throw new IllegalArgumentException(s"No task for ${taskId} is found")
          case Some(ref) if ref.status != TaskStatus.QUEUED =>
            // ok
            ref
          case Some(ref) =>
            Thread.sleep(100 * (retryCount + 1))
            loop(retryCount + 1)
        }
      }
    }
    loop(0)
  }

}
