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

import wvlet.airframe.http.RPC
import wvlet.airframe.metrics.ElapsedTime
import wvlet.airframe.ulid.ULID
import wvlet.flow.api.BuildInfo

import java.time.Instant

object ServiceInfoApi {
  case class ServiceInfo(
      version: String = BuildInfo.version,
      buildTime: String = BuildInfo.builtAtString,
      upTime: ElapsedTime
  )
}

import ServiceInfoApi._

/**
  */
@RPC
class ServiceInfoApi(id: ULID = ULID.newULID, serviceStartTime: Instant = Instant.now()) {

  /**
    * Return the process identifier that can be used for checking whether the node is retarted or not
    */
  def launchId: ULID = id
  def serviceInfo: ServiceInfo = {
    val uptimeMillis = Instant.now().toEpochMilli - serviceStartTime.toEpochMilli
    ServiceInfo(upTime = ElapsedTime.succinctMillis(uptimeMillis))
  }
}
