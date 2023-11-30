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

import wvlet.airframe.control.Control
import wvlet.airframe.http.{Http, ServerAddress}
import wvlet.dataflow.api.internal.coordinator.CoordinatorRPC
import wvlet.dataflow.api.internal.worker.WorkerRPC
import wvlet.dataflow.server.ServerModule.{CoordinatorClient, WorkerClient}
import wvlet.log.LogSupport

import java.util.concurrent.ConcurrentHashMap
import scala.annotation.tailrec

/**
  * Provide gRPC clients and closes created clients when closing this provider
  */
class RPCClientProvider extends LogSupport with AutoCloseable:

  import scala.jdk.CollectionConverters.*

  private val clientHolder = new ConcurrentHashMap[String, AutoCloseable]().asScala

  def getCoordinatorClient(name: String, nodeAddress: ServerAddress): CoordinatorClient =
    clientHolder
      .getOrElseUpdate(
        nodeAddress.toString,
        new CoordinatorClient(
          Http.client
            .withName(name)
            .newSyncClient(nodeAddress.hostAndPort)
        )
      ).asInstanceOf[CoordinatorClient]

  def getWorkerClient(name: String, nodeAddress: ServerAddress): WorkerClient =
    clientHolder
      .getOrElseUpdate(
        nodeAddress.toString,
        new WorkerClient(
          Http.client
            .withName(name)
            .newSyncClient(nodeAddress.hostAndPort)
        )
      ).asInstanceOf[WorkerClient]

  override def close(): Unit =
    Control.closeResources(clientHolder.values.toSeq*)
