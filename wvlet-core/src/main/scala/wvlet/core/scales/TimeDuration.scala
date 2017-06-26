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
package wvlet.core.scales

import java.time.{DayOfWeek, ZonedDateTime}
import java.time.temporal.ChronoUnit


case class TimeDuration(x:Long, unit:ChronoUnit) {

  def fromOffset(offset:ZonedDateTime): TimeWindow = {
    val base = unit match {
      case ChronoUnit.SECONDS | ChronoUnit.MINUTES |  ChronoUnit.HOURS | ChronoUnit.DAYS =>
        offset.truncatedTo(unit)
      case ChronoUnit.WEEKS =>
        offset.truncatedTo(ChronoUnit.DAYS).`with`(DayOfWeek.MONDAY)
      case ChronoUnit.MONTHS =>
        offset.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)
      case ChronoUnit.YEARS =>
        offset.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS)
      case other =>
        throw new UnsupportedOperationException(s"${other} is not supported")
    }
    val next = base.plus(x, unit)
    if(x <= 0) {
      TimeWindow(next, offset)
    }
    else {
      TimeWindow(offset, next)
    }
  }

}

object TimeDuration {

  def apply(s:String): TimeDuration = {
    val durationPattern = "^([+-]|last|next)?([0-9]+)(s|m|d|h|w|M|y)".r("prefix", "num", "unit", "o")

    durationPattern.findFirstMatchIn(s) match {
      case None =>
        throw new IllegalArgumentException(s"Invalid duration: ${s}")
      case Some(m) =>
        val length = m.group("num").toInt
        val unit = unitOf(m.group("unit"))
        m.group("prefix") match {
          case null | "-" | "last" =>
            TimeDuration(-length, unit)
          case "+" | "next" =>
            TimeDuration(length, unit)
          case other =>
            throw new IllegalArgumentException(s"Unknown duration prefix: ${other}")
        }
    }
  }

  private val unitTable: Map[String, ChronoUnit] = Map(
    "s" -> ChronoUnit.SECONDS,
    "m" -> ChronoUnit.MINUTES,
    "d"-> ChronoUnit.DAYS,
    "h" -> ChronoUnit.HOURS,
    "w" -> ChronoUnit.WEEKS,
    "M" -> ChronoUnit.MONTHS,
    "y" -> ChronoUnit.YEARS
  )

  private[scales] def unitOf(s:String) : ChronoUnit = {
    unitTable.getOrElse(s, throw new IllegalArgumentException(s"Unknown unit type ${s}"))
  }

}