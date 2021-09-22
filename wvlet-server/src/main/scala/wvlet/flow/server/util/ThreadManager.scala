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
package wvlet.flow.server.util

import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{ForkJoinPool, ForkJoinWorkerThread}

class ThreadManager(name: String, numThreads: Int = Runtime.getRuntime.availableProcessors() * 2)
    extends AutoCloseable {
  private val executorService = {
    new ForkJoinPool(
      // The number of threads
      numThreads,
      new ForkJoinWorkerThreadFactory() {
        private val threadCount = new AtomicInteger()
        override def newThread(pool: ForkJoinPool): ForkJoinWorkerThread = {
          val thread     = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool)
          val threadName = s"${name}-${threadCount.getAndIncrement()}"
          thread.setDaemon(true)
          thread.setName(threadName)
          thread
        }
      },
      // Use the default behavior for unrecoverable exceptions
      null,
      // Enable asyncMode
      true
    )
  }
  def submit[U](body: => U): Unit = {
    executorService.submit(new Runnable {
      override def run(): Unit = body
    })
  }
  override def close(): Unit = executorService.shutdownNow()
}
