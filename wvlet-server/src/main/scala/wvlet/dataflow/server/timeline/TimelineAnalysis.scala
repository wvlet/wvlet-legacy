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
package wvlet.dataflow.server.timeline

import wvlet.dataflow.api.v1.timeline.TimeInterval
import wvlet.log.LogSupport

import scala.collection.mutable

object TimelineAnalysis extends LogSupport {
  def findPeakTimes(list: Seq[TimeInterval]): Unit = {
    if (list.isEmpty) {
      // do nothing
    } else {
      val sorted    = list.sortBy(_.startAt).toIndexedSeq
      val length    = sorted.length
      var sweepLine = sorted(0).startAt
      var index     = 1
      // sorted intervals on endAt values
      val overlappedIntervals = new mutable.PriorityQueue[TimeInterval]()(TimeInterval.biggerEndOrdering)
      overlappedIntervals += sorted(0)

      def report: Unit = {
        if (overlappedIntervals.length > 1) {
          info(s"Peak time: ${sweepLine}: ${overlappedIntervals.size}")
        }
      }

      while (index < length) {
        val x = sorted(index)
        if (overlappedIntervals.isEmpty || x.startAt < overlappedIntervals.head.endAt) {
          overlappedIntervals.enqueue(x)
        }
        // Remove all intervals that end before x.startAt
        while (overlappedIntervals.nonEmpty && overlappedIntervals.head.endAt <= x.startAt) {
          val r = overlappedIntervals.dequeue()
          if (sweepLine < r.endAt) {
            sweepLine = r.endAt
            report
          }
        }
        if (sweepLine < x.startAt) {
          sweepLine = x.startAt
          report
        }
        index += 1
      }

      while (overlappedIntervals.nonEmpty) {
        val r = overlappedIntervals.dequeue()
        sweepLine = r.endAt
        report
      }
    }
  }
}
