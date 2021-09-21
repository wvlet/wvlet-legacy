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

import io.grpc.ManagedChannelBuilder
import wvlet.airframe.http.grpc.{GrpcServer, GrpcServerConfig, gRPC}
import wvlet.airframe.http.{Router, ServerAddress}
import wvlet.airframe.{Design, Session, newDesign}
import wvlet.flow.api.WvletGrpcClient
import wvlet.flow.api.v1.ServiceInfoApi
import wvlet.flow.server.api.{CoordinatorApiImpl, WorkerApiImpl}

import java.net.ServerSocket

case class CoordinatorConfig(
    name: String = "coordinator",
    // self-address
    serverAddress: ServerAddress
) {
  def port: Int = serverAddress.port
}

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
  type CoordinatorClient = WvletGrpcClient.SyncClient
  type CoordinatorServer = GrpcServer
  type WorkerServer      = GrpcServer

  def coordinatorRouter = Router
    .add[ServiceInfoApi]
    .add[CoordinatorApiImpl]

  def workerRouter = Router
    .add[ServiceInfoApi]
    .add[WorkerApiImpl]

  private def coordinatorServer(config: CoordinatorConfig): GrpcServerConfig =
    gRPC.server
      .withName(config.name)
      .withPort(config.port)
      .withRouter(coordinatorRouter)

  private def workerServer(config: WorkerConfig): GrpcServerConfig =
    gRPC.server
      .withName(config.name)
      .withPort(config.port)
      .withRouter(workerRouter)

  def coordinatorDesign(config: CoordinatorConfig): Design = {
    newDesign
      .bind[CoordinatorConfig].toInstance(config)
      .bind[CoordinatorServer].toProvider { session: Session => coordinatorServer(config).newServer(session) }
  }

  def workerDesign(config: WorkerConfig): Design = {
    WorkerService.design
      .bind[WorkerConfig].toInstance(config)
      .bind[WorkerServer].toProvider { session: Session => workerServer(config).newServer(session) }
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
        val channel = ManagedChannelBuilder
          .forTarget(server.localAddress)
          .maxInboundMessageSize(32 * 1024 * 1024)
          .usePlaintext()
          .build()
        WvletGrpcClient.newSyncClient(channel)
      }
      // Add this design to start up worker service early
      .bind[WorkerService].toEagerSingleton

  }
}
