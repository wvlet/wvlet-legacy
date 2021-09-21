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
package wvlet.flow.server

import wvlet.airframe.{Design, newDesign}
import wvlet.flow.api.internal.Cluster.Node
import wvlet.flow.server.ServerModule.WorkerServer
import wvlet.flow.server.WorkerService.WorkerBackgroundExecutor

import java.net.InetAddress
import java.time.Instant
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

/**
  */
/**
  */
class WorkerService(
    workerConfig: WorkerConfig,
    // Adding this dependency to start WorkerServer
    workerServer: WorkerServer,
    rpcClientProvider: RPCClientProvider,
    executor: WorkerBackgroundExecutor
) {

  private val self: Node = {
    val localHost = InetAddress.getLocalHost
    val localAddr = s"${localHost.getHostAddress}:${workerConfig.serverAddress.port}"
    Node(name = workerConfig.name, address = localAddr, isCoordinator = false, startedAt = Instant.now())
  }

  private lazy val coordinatorClient = rpcClientProvider.getCoordinatorClient(workerConfig.coordinatorAddress)

  // Polling coordinator every 5 seconds
  executor.scheduleAtFixedRate(
    () => { coordinatorClient.CoordinatorApi.register(self) },
    0,
    5,
    TimeUnit.SECONDS
  )

}

object WorkerService {

  type WorkerBackgroundExecutor = ScheduledExecutorService

  def design: Design = newDesign
    .bind[WorkerBackgroundExecutor].toInstance(
      Executors.newSingleThreadScheduledExecutor()
    )
    .onShutdown(_.shutdownNow())

}
