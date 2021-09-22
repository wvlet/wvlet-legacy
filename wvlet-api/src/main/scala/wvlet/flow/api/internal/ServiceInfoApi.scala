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
package wvlet.flow.api.internal

import wvlet.airframe.metrics.ElapsedTime
import wvlet.airframe.ulid.ULID
import wvlet.flow.api.BuildInfo

import java.time.Instant

case class ServiceInfo(
    launchId: ULID,
    version: String = BuildInfo.version,
    buildTime: String = BuildInfo.builtAtString,
    upTime: ElapsedTime
)

/**
  * A base trait for returning the server process information
  */
abstract trait ServiceInfoApi {

  /**
    * The process identifier that can be used for checking whether the node is restarted or not
    */
  private val id: ULID                  = ULID.newULID
  private val serviceStartTime: Instant = Instant.now()

  def serviceInfo: ServiceInfo = {
    val uptimeMillis = Instant.now().toEpochMilli - serviceStartTime.toEpochMilli
    ServiceInfo(launchId = id, upTime = ElapsedTime.succinctMillis(uptimeMillis))
  }
}
