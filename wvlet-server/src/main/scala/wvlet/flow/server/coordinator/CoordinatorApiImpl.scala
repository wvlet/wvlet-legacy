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
package wvlet.flow.server.coordinator

import wvlet.flow.api.internal.Cluster.{Node, NodeInfo}
import wvlet.flow.api.internal.coordinator.CoordinatorApi

/**
  */
class CoordinatorApiImpl(nodeManager: NodeManager) extends CoordinatorApi {
  override def listNodes: Seq[NodeInfo] = {
    nodeManager.listNodes
  }
  override def register(node: Node): Unit = {
    nodeManager.heartBeat(node)
  }
}
