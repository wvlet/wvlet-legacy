package wvlet.core.scales

import java.time.temporal.ChronoUnit
import java.time._

import wvlet.log.LogSupport

import scala.util.{Failure, Success, Try}


case class TimeWindow(start:ZonedDateTime, end:ZonedDateTime) {

  private def instantOfStart = start.toInstant
  private def instantOfEnd = end.toInstant

  def startUnixTime = instantOfStart.getEpochSecond
  def endUnixTime = instantOfEnd.getEpochSecond

  def startEpochMillis = instantOfStart.toEpochMilli
  def endEpochMillis = instantOfEnd.toEpochMilli

  override def toString = {
    val s = TimeStampFormatter.formatTimestamp(startEpochMillis)
    val e = TimeStampFormatter.formatTimestamp(endEpochMillis)
    s"[${s}, ${e})"
  }

  private def splitInto(unit: ChronoUnit): Seq[TimeWindow] = {
    val b = Seq.newBuilder[TimeWindow]
    var cursor = start
    while (cursor.compareTo(end) < 0) {
      val e = unit match {
        case ChronoUnit.DAYS | ChronoUnit.HOURS | ChronoUnit.MINUTES =>
          cursor.plus(1, unit).truncatedTo(unit)
        case ChronoUnit.WEEKS =>
          cursor.plus(1, unit).`with`(DayOfWeek.MONDAY)
        case ChronoUnit.MONTHS =>
          cursor.plus(1, unit).withDayOfMonth(1)
        case ChronoUnit.YEARS =>
          cursor.plus(1, unit).withDayOfYear(1)
        case other =>
          throw new IllegalStateException(s"Invalid split unit ${unit} for range ${toString}")
      }
      if (e.compareTo(end) <= 0) {
        b += TimeWindow(cursor, e)
      }
      else {
        b += TimeWindow(cursor, end)
      }
      cursor = e
    }
    b.result()
  }

  def splitIntoHours: Seq[TimeWindow] = splitInto(ChronoUnit.HOURS)
  def splitIntoDays: Seq[TimeWindow] = splitInto(ChronoUnit.DAYS)
  def splitIntoMonths: Seq[TimeWindow] = splitInto(ChronoUnit.MONTHS)
  def splitIntoWeeks: Seq[TimeWindow] = splitInto(ChronoUnit.WEEKS)
}


object TimeWindow {

  val systemZone: ZoneOffset = {
    // Need to get the current ZoneOffset to resolve PDT, etc.
    // because ZoneID of America/Los Angels (PST) is -0800 while PDT zone offset is -0700
    val z = ZoneId.systemDefault().normalized() // This returns America/Los Angels (PST)
    ZonedDateTime.now(z).getOffset
  }
  val UTC: ZoneOffset        = ZoneOffset.UTC

  def of(zoneId:ZoneOffset): TimeWindowBuilder = new TimeWindowBuilder(zoneId)
  def ofUTC: TimeWindowBuilder = of(UTC)
  def ofSystem: TimeWindowBuilder = of(systemZone)

  def truncateTo(t:ZonedDateTime, unit:ChronoUnit): ZonedDateTime = {
    unit match {
      case ChronoUnit.SECONDS | ChronoUnit.MINUTES |  ChronoUnit.HOURS | ChronoUnit.DAYS =>
        t.truncatedTo(unit)
      case ChronoUnit.WEEKS =>
        t.truncatedTo(ChronoUnit.DAYS).`with`(DayOfWeek.MONDAY)
      case ChronoUnit.MONTHS =>
        t.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)
      case ChronoUnit.YEARS =>
        t.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS)
      case other =>
        throw new UnsupportedOperationException(s"${other} is not supported")
    }
  }

}

class TimeWindowBuilder(zone:ZoneOffset, currentTime:Option[ZonedDateTime]=None) extends LogSupport {

  def withCurrentTime(t:ZonedDateTime): TimeWindowBuilder = new TimeWindowBuilder(zone, Some(t))

  def now = currentTime.getOrElse(ZonedDateTime.now(zone))
  def beginningOfTheHour = now.truncatedTo(ChronoUnit.HOURS)
  def endOfTheHour = beginningOfTheHour.plusHours(1)
  def beginningOfTheDay = now.truncatedTo(ChronoUnit.DAYS)
  def endOfTheDay = beginningOfTheDay.plusDays(1)
  def beginningOfTheWeek = now.truncatedTo(ChronoUnit.DAYS).`with`(DayOfWeek.MONDAY)
  def endOfTheWeek = beginningOfTheWeek.plusWeeks(1)
  def beginningOfTheMonth = now.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS)
  def endOfTheMonth = beginningOfTheMonth.plusMonths(1)
  def beginningOfTheYear = now.withDayOfYear(1).truncatedTo(ChronoUnit.DAYS)
  def endOfTheYear = beginningOfTheYear.plusYears(1)

  def today = TimeWindow(beginningOfTheDay, endOfTheDay)
  def thisHour = TimeWindow(beginningOfTheHour, endOfTheHour)
  def thisWeek = TimeWindow(beginningOfTheWeek, endOfTheWeek)
  def thisMonth = TimeWindow(beginningOfTheMonth, endOfTheMonth)
  def thisYear = TimeWindow(beginningOfTheYear, endOfTheYear)

  private def parseOffset(o:String, unit:ChronoUnit): ZonedDateTime = {
    o match {
      case null => TimeWindow.truncateTo(now, unit)
      case "now" => now
      case other =>
        Try(TimeDuration(o)) match {
          case Success(x) =>
            x.fromOffset(now).start
          case Failure(e) =>
            DateTimeParser.parse(o, zone).getOrElse {
              throw new IllegalArgumentException(s"Invalid offset string: ${o}")
            }
        }
    }
  }

  private def parsePrimitiveDuration(d:String): Option[TimeWindow] = {
    val result = d match {
      // current
      case "thisHour" => thisHour
      case "today" => today
      case "thisWeek" => thisWeek
      case "thisMonth" => thisMonth
      case "thisYear" => thisYear
      // past
      case "lastHour" => TimeWindow(beginningOfTheHour.minusHours(1), beginningOfTheHour)
      case "yesterday" => TimeWindow(beginningOfTheDay.minusDays(1), beginningOfTheDay)
      case "lastWeek" => TimeWindow(beginningOfTheWeek.minusWeeks(1), beginningOfTheWeek)
      case "lastMonth" => TimeWindow(beginningOfTheMonth.minusMonths(1), beginningOfTheMonth)
      case "lastYear" => TimeWindow(beginningOfTheYear.minusYears(1), beginningOfTheYear)
      // future
      case "nextHour" => TimeWindow(endOfTheHour, endOfTheHour.plusHours(1))
      case "tomorrow" => TimeWindow(endOfTheDay, endOfTheDay.plusDays(1))
      case "nextWeek" => TimeWindow(endOfTheWeek, endOfTheWeek.plusWeeks(1))
      case "nextMonth" => TimeWindow(endOfTheMonth, endOfTheMonth.plusMonths(1))
      case "nextYear" => TimeWindow(endOfTheYear, endOfTheYear.plusYears(1))
      case other => null
    }
    Option(result)
  }

  def parse(str:String): TimeWindow = {
    val pattern = s"^([^/]+)(/(.*))?".r("duration", "sep", "offset")
    pattern.findFirstMatchIn(str) match {
      case Some(m) =>
        val d = m.group("duration")
        parsePrimitiveDuration(d).getOrElse {
          val duration = TimeDuration(d)
          val offset = parseOffset(m.group("offset"), duration.unit)
          duration.fromOffset(offset)
        }
      case None =>
        throw new IllegalArgumentException(s"TimeRange.of(${str})")
    }
  }
}
