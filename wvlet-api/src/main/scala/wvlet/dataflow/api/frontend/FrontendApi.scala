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
package wvlet.dataflow.api.frontend

import wvlet.airframe.http.*
import wvlet.dataflow.api.ServiceInfoApi
import FrontendApi.*
import wvlet.dataflow.api.v1.timeline.TimeInterval

import java.time.Instant

@RPC
trait FrontendApi extends ServiceInfoApi:
  def getTimeline(request: TimelineRequest): TimelineResponse

object FrontendApi extends RxRouterProvider:
  override def router: RxRouter = RxRouter.of[FrontendApi]

  case class TimelineRequest(
      timeWindow: String = "-1h/now"
  )
  case class TimelineResponse(
      startAt: Long,
      endAt: Long,
      entries: Seq[TimeInterval]
  )
