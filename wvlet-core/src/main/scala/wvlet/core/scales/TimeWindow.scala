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

  def parse(str:String): TimeWindow = {
    val pattern = s"^([^/]+)(/(.*))?".r("duration", "sep", "offset")
    pattern.findFirstMatchIn(str) match {
      case Some(m) =>
        val duration = TimeDuration(m.group("duration"))
        val offset = parseOffset(m.group("offset"), duration.unit)
        duration.fromOffset(offset)
      case None =>
        throw new IllegalArgumentException(s"TimeRange.of(${str})")
    }
  }

  def now = currentTime.getOrElse(ZonedDateTime.now(zone))
}
