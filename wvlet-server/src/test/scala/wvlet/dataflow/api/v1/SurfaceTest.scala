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

import wvlet.airframe.codec.MessageCodecFactory
import wvlet.airframe.surface.Surface
import wvlet.airspec.AirSpec
import wvlet.dataflow.server.coordinator.{NodeManager, TaskManager, TaskManagerConfig, TaskStateListener}
import wvlet.dataflow.api.internal.worker.{WorkerApi, WorkerRPC}
import wvlet.dataflow.api.internal.coordinator.CoordinatorRPC

class SurfaceTest extends AirSpec {
  test("create surface of Api request/response types") {
    val s = Surface.of[TaskRef]
    info(s)

    val s2 = Surface.of[TaskRequest]
    info(s2)

    val s3 = Surface.of[Option[TaskRef]]
    info(s3)

    val s4 = Surface.of[TaskListRequest]
    info(s4)

    val s5 = Surface.of[TaskList]
    info(s5)
  }

  test("surface of TaskManager") {
    val p = Surface.of[TaskManager].params.find(_.name == "listeners").get
  }

  test("surface of WorkerApi") {
    Surface.of[WorkerRPC.internal.WorkerApiInternals.__runTask_request]
    Surface.of[WorkerApi.TaskExecutionInfo]
  }

  test("coordinator surface") {
    val s = Surface.of[CoordinatorRPC.internal.CoordinatorApiInternals.__updateTaskStatus_request]
    info(s)
  }
}
