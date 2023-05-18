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

import wvlet.airspec.AirSpec
import wvlet.dataflow.api.v1.timeline.TimeInterval
import wvlet.dataflow.server.timeline.TimelineAnalysis.PeakChange

class TimelineAnalysisTest extends AirSpec {
  test("sweep") {
    // 1  2      5  6 7  8      10   12 13 14 15 16
    // |---------|              |----|  |-----|
    //    |-----------|         |----|      |-----|
    //              |---|
    val lst = List(
      TimeInterval("i1", 1, 5),
      TimeInterval("i2", 2, 7),
      TimeInterval("i3", 6, 8),
      TimeInterval("i4", 10, 12),
      TimeInterval("i5", 10, 12),
      TimeInterval("i6", 13, 15),
      TimeInterval("i7", 14, 16)
    )

    val peaks = Seq.newBuilder[PeakChange]

    TimelineAnalysis.findPeakTimes(
      lst,
      reporter = (x: PeakChange) => {
        peaks += x
      }
    )

    peaks.result() shouldBe Seq(
      PeakChange(1, 1),
      PeakChange(2, 2),
      PeakChange(5, 1),
      PeakChange(6, 2),
      PeakChange(7, 1),
      PeakChange(8, 0),
      PeakChange(10, 2),
      PeakChange(12, 0),
      PeakChange(13, 1),
      PeakChange(14, 2),
      PeakChange(15, 1),
      PeakChange(16, 0)
    )
  }

}
