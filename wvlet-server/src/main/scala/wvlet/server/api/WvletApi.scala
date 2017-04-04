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
package wvlet.server.api

import com.google.inject.Module
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.filter.Cors.HttpFilter
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

/**
  *
  */
trait WvletApi extends HttpServer {

  override protected def modules: Seq[Module] = Seq.empty

  override def configureHttp(router: HttpRouter) {
    router
    .filter[CommonFilters]
    .filter(new HttpFilter(Cors.UnsafePermissivePolicy))
    .add[StatusController]
    .add[ProjectController]
  }
}




