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
package wvlet.flow.plugin.trino

import okhttp3.OkHttpClient
import wvlet.airframe.Design
import wvlet.log.LogSupport

import java.util.concurrent.TimeUnit

/**
  */
object OkHttpClientService extends LogSupport {
  def design: Design =
    Design.newDesign
      .bind[OkHttpClient].toInstance {
        info(s"Starting OkHttp client")
        val builder = new OkHttpClient.Builder
        builder
          .connectTimeout(30, TimeUnit.SECONDS)
          .readTimeout(1, TimeUnit.MINUTES)
          .writeTimeout(1, TimeUnit.MINUTES)
        builder.build
      }
      .onShutdown { okHttpClient =>
        info(s"Closing OkHttp client")
        okHttpClient.dispatcher().executorService().shutdown()
        okHttpClient.connectionPool().evictAll()
      }
}
