package wvlet.core.scales

import java.time.temporal.ChronoUnit
import java.time._


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

  val systemZone = ZoneId.systemDefault().normalized()
  val UTC        = ZoneOffset.UTC


  def of(zoneId:ZoneId): TimeWindowBuilder = new TimeWindowBuilder(zoneId)
  def ofUTC: TimeWindowBuilder = new TimeWindowBuilder(UTC)
  def ofSystem: TimeWindowBuilder = new TimeWindowBuilder(systemZone)

}



class TimeWindowBuilder(zone:ZoneId = TimeWindow.systemZone) {

  private def parseOffset(o:String, unit:ChronoUnit): ZonedDateTime = {
    o match {
      case null => now.truncatedTo(unit)
      case "now" => now
      case other =>

    }
  }

  def parse(str:String): TimeWindow = {
    val pattern = s"^([^/]+)(/(.*))?".r("duration", "sep", "offset")
    pattern.findFirstMatchIn(str) match {
      case Some(m) =>
        val duration = TimeDuration(m.group("duration"))
        val offset = parseOffset(m.group("offset"))
        duration.fromOffset(offset)
      case None =>
        throw new IllegalArgumentException(s"TimeRange.of(${str})")
    }
  }


  def now = ZonedDateTime.now(zone)
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


  case class next(x: Long) {
    def of(unit: ChronoUnit): TimeWindow = {
      unit match {
        case ChronoUnit.SECONDS => seconds
        case ChronoUnit.MINUTES => minutes
        case ChronoUnit.HOURS => hours
        case ChronoUnit.DAYS => days
        case ChronoUnit.WEEKS => weeks
        case ChronoUnit.MONTHS => months
        case ChronoUnit.YEARS => years
        case other =>
          throw new UnsupportedOperationException(s"next.of(${unit}")
      }
    }

    def fromNow(unit: ChronoUnit): TimeWindow = {
      unit match {
        case ChronoUnit.SECONDS => seconds
        case ChronoUnit.MINUTES => minutes
        case ChronoUnit.HOURS => hoursFromNow
        case ChronoUnit.DAYS => daysFromNow
        case ChronoUnit.WEEKS => weeksFromNow
        case ChronoUnit.MONTHS => monthsFromNow
        case ChronoUnit.YEARS => yearsFromNow
        case other =>
          throw new UnsupportedOperationException(s"next.fromNow(${unit}")
      }
    }

    def seconds = {
      val start = now.truncatedTo(ChronoUnit.SECONDS)
      TimeWindow(start, start.plusSeconds(x))
    }

    def minutes = {
      val start = now.truncatedTo(ChronoUnit.MINUTES)
      TimeWindow(start, start.plusMinutes(x))
    }

    def hours = {
      val currentHour = now.truncatedTo(ChronoUnit.HOURS)
      TimeWindow(currentHour, currentHour.plusHours(x))
    }

    def days = {
      val start = beginningOfTheDay
      TimeWindow(start, start.plus(x, ChronoUnit.DAYS))
    }
    def weeks = {
      val start = beginningOfTheWeek
      TimeWindow(start, start.plusWeeks(x))
    }
    def months = {
      val start = beginningOfTheMonth
      TimeWindow(start, start.plusMonths(x))
    }
    def years = {
      val start = beginningOfTheYear
      TimeWindow(start, start.plusYears(x))
    }

    def hoursFromNow = TimeWindow(now, hours.end)
    def daysFromNow =  TimeWindow(now, days.end)
    def weeksFromNow = TimeWindow(now, weeks.end)
    def monthsFromNow = TimeWindow(now, weeks.end)
    def yearsFromNow = TimeWindow(now, years.end)
  }

  case class last(x: Long) {
    def of(unit: ChronoUnit): TimeWindow = {
      unit match {
        case ChronoUnit.SECONDS => seconds
        case ChronoUnit.MINUTES => minutes
        case ChronoUnit.HOURS => hours
        case ChronoUnit.DAYS => days
        case ChronoUnit.WEEKS => weeks
        case ChronoUnit.MONTHS => months
        case ChronoUnit.YEARS => years
        case other =>
          throw new UnsupportedOperationException(s"last.of(${unit}")
      }
    }

    def untilNow(unit: ChronoUnit): TimeWindow = {
      unit match {
        case ChronoUnit.SECONDS => seconds
        case ChronoUnit.MINUTES => minutes
        case ChronoUnit.HOURS => hoursUntilNow
        case ChronoUnit.DAYS => daysUntilNow
        case ChronoUnit.WEEKS => weeksUntilNow
        case ChronoUnit.MONTHS => monthsUntilNow
        case ChronoUnit.YEARS => yearsUntilNow
        case other =>
          throw new UnsupportedOperationException(s"last.fromNow(${unit}")
      }
    }

    def seconds = {
      val end = now.truncatedTo(ChronoUnit.SECONDS)
      val start = end.minusSeconds(x)
      TimeWindow(start, end)
    }

    def minutes = {
      val end = now.truncatedTo(ChronoUnit.MINUTES)
      val start = end.minusMinutes(x)
      TimeWindow(start, end)
    }

    def hours = {
      val currentHour = now.truncatedTo(ChronoUnit.HOURS)
      val start = currentHour.minusHours(x)
      TimeWindow(start, currentHour)
    }

    def hoursUntilNow = {
      TimeWindow(hours.start, now)
    }

    def days = {
      val now = beginningOfTheDay
      val start = now.minus(x, ChronoUnit.DAYS)
      TimeWindow(start, now)
    }

    def daysUntilNow = {
      val d = days
      TimeWindow(d.start, now)
    }

    def weeks = {
      val end = beginningOfTheWeek
      val start = end.minusWeeks(x)
      TimeWindow(start, end)
    }

    def weeksUntilNow = {
      TimeWindow(weeks.start, now)
    }

    def months = {
      val now = beginningOfTheMonth
      val start = now.minusMonths(x)
      TimeWindow(start, now)
    }

    def monthsUntilNow = {
      TimeWindow(months.start, now)
    }

    def years = {
      val end = beginningOfTheYear
      val start = end.minusYears(x)
      TimeWindow(start, end)
    }

    def yearsUntilNow = TimeWindow(years.start, now)
  }

}
