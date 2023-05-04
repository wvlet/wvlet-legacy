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
package wvlet.dataflow.server

import wvlet.airframe.http.netty.{Netty, NettyServer, NettyServerConfig}
import wvlet.airframe.http.{Http, Router, RxRouter, ServerAddress}
import wvlet.airframe.{Design, Session, newDesign}
import wvlet.dataflow.api.internal.ServiceInfoApi
import wvlet.dataflow.api.internal.coordinator.CoordinatorRPC
import wvlet.dataflow.api.internal.worker.WorkerRPC
import wvlet.dataflow.api.v1.WvletRPC
import wvlet.dataflow.server.coordinator.{CoordinatorApiImpl, CoordinatorConfig, TaskApiImpl, TaskManager}
import wvlet.dataflow.server.worker.{WorkerApiImpl, WorkerService}

import java.net.ServerSocket

case class WorkerConfig(
    name: String = "worker-1",
    serverAddress: ServerAddress,
    coordinatorAddress: ServerAddress
) {
  def port: Int = serverAddress.port
}

/**
  */
object ServerModule {
  type CoordinatorClient = CoordinatorRPC.RPCSyncClient
  type CoordinatorServer = NettyServer
  type WorkerServer      = NettyServer
  type WorkerClient      = WorkerRPC.RPCSyncClient
  type ApiClient         = WvletRPC.RPCSyncClient

  def coordinatorRouter = RxRouter.of(
    RxRouter.of[ServiceInfoApi],
    RxRouter.of[CoordinatorApiImpl],
    RxRouter.of[TaskApiImpl]
  )

  def workerRouter = RxRouter.of(
    RxRouter.of[ServiceInfoApi],
    RxRouter.of[WorkerApiImpl]
  )

  private def coordinatorServer(config: CoordinatorConfig): NettyServerConfig =
    Netty.server
      .withName(config.name)
      .withPort(config.port)
      .withRouter(coordinatorRouter)

  private def workerServer(config: WorkerConfig): NettyServerConfig =
    Netty.server
      .withName(config.name)
      .withPort(config.port)
      .withRouter(workerRouter)

  def coordinatorDesign(config: CoordinatorConfig): Design = {
    newDesign
      .bind[CoordinatorConfig].toInstance(config)
      .bind[CoordinatorServer].toProvider { (session: Session) => coordinatorServer(config).newServer(session) }
      .add(TaskManager.design)
  }

  def workerDesign(config: WorkerConfig): Design = {
    WorkerService.design
      .bind[WorkerConfig].toInstance(config)
      .bind[WorkerServer].toProvider { (session: Session) => workerServer(config).newServer(session) }
      .add(PluginManager.design)
  }

  private def randomPort(num: Int): Seq[Int] = {
    val sockets = (0 until num).map(i => new ServerSocket(0))
    val ports   = sockets.map(_.getLocalPort).toIndexedSeq
    sockets.foreach(_.close())
    ports
  }

  def standaloneDesign(coordinatorPort: Int, workerPort: Int): Design = {
    coordinatorDesign(CoordinatorConfig(serverAddress = ServerAddress(s"localhost:${coordinatorPort}")))
      .add(
        workerDesign(
          WorkerConfig(
            serverAddress = ServerAddress(s"localhost:${workerPort}"),
            coordinatorAddress = ServerAddress(s"localhost:${coordinatorPort}")
          )
        )
      )
  }

  /**
    * Design for launching a test server and client
    * @return
    */
  def testServerAndClient: Design = {
    val Seq(coordinatorPort, workerPort) = randomPort(2)

    coordinatorDesign(CoordinatorConfig(serverAddress = ServerAddress(s"localhost:${coordinatorPort}")))
      .add(
        workerDesign(
          WorkerConfig(
            serverAddress = ServerAddress(s"localhost:${workerPort}"),
            coordinatorAddress = ServerAddress(s"localhost:${coordinatorPort}")
          )
        )
      )
      .bind[CoordinatorClient].toProvider { (server: CoordinatorServer) =>
        CoordinatorRPC.newRPCSyncClient(Http.client.newSyncClient(server.localAddress))
      }
      .bind[ApiClient].toProvider { (server: CoordinatorServer) =>
        WvletRPC.newRPCSyncClient(Http.client.newSyncClient(server.localAddress))
      }
      // Add this design to start up worker service early
      .bind[WorkerService].toEagerSingleton

  }
}
