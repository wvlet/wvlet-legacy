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

import wvlet.airframe.Design
import wvlet.airframe.http.{Router, ServerAddress}
import wvlet.airframe.http.grpc.{GrpcServer, GrpcServerConfig, gRPC}
import wvlet.flow.api.WvletGrpcClient
import wvlet.flow.api.v1.ServiceInfoApi

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

//  def workerRouter = Router
//    .add[ServerInfoApi]
//    .add[WorkerApiImpl]

  private def coordinatorServer(config: CoordinatorConfig): GrpcServerConfig =
    gRPC.server
      .withName(config.name)
      .withPort(config.port)
      .withRouter(coordinatorRouter)

//  private def workerServer(config: WorkerConfig): GrpcServerConfig =
//    gRPC.server
//      .withName(config.name)
//      .withPort(config.port)
//      .withRouter(workerRouter)

  def design: Design =
    coordinatorServer()
}
