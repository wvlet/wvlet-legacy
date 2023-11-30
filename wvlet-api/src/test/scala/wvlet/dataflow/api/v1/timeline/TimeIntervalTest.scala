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
package wvlet.dataflow.api.v1.timeline

import wvlet.airspec.AirSpec

import java.util.concurrent.TimeUnit

class TimeIntervalTest extends AirSpec:
  test("time interval predicates") {
    val t0 = TimeInterval("t0", 50L, 80L)
    val t1 = TimeInterval("t1", 100L, 200L)
    val t2 = TimeInterval("t2", 150L, 230L)

    test("has name") {
      t0.name shouldBe "t0"
      t1.name shouldBe "t1"
      t2.name shouldBe "t2"
    }

    test("precedes") {
      t0.precedes(t1) shouldBe true
      t0.precedes(t2) shouldBe true
      t1.precedes(t0) shouldBe false
      t1.precedes(t2) shouldBe false
      t2.precedes(t0) shouldBe false
      t2.precedes(t1) shouldBe false
    }

    test("follows") {
      t0.follows(t1) shouldBe false
      t0.follows(t2) shouldBe false
      t1.follows(t0) shouldBe true
      t1.follows(t2) shouldBe false
      t2.follows(t0) shouldBe true
      t2.follows(t1) shouldBe false
    }

    test("overlap") {
      t0.overlaps(t0) shouldBe true
      t0.overlaps(t1) shouldBe false
      t0.overlaps(t2) shouldBe false

      t1.overlaps(t0) shouldBe false
      t1.overlaps(t1) shouldBe true
      t1.overlaps(t2) shouldBe true

      t2.overlaps(t0) shouldBe false
      t2.overlaps(t1) shouldBe true
      t2.overlaps(t2) shouldBe true
    }

    test("compare") {
      t0.compare(t1) < 0 shouldBe true
      t0.compare(t0) shouldBe 0
      t1.compare(t2) < 0 shouldBe true
    }
  }
