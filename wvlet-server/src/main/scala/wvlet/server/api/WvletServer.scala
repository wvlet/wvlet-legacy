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

import io.finch._
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.param.Stats
import com.twitter.server.TwitterServer
import com.twitter.util.Await

/**
  *
  */
object WvletServer extends TwitterServer {

  lazy val server = Http.server
                    .configured(Stats(statsReceiver))
                    .serve(":8080", WvletApi.service)

  def main(): Unit = {
    onExit {server.close()}
    Await.ready(server)
  }
}



