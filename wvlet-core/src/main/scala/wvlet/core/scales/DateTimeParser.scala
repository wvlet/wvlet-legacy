package wvlet.core.scales

import java.time.format.DateTimeFormatter
import java.time._

import wvlet.log.LogSupport

import scala.util.{Failure, Success, Try}

/**
  *
  */
object DateTimeParser extends LogSupport{

  def localDateTimePattern = "yyyy[-MM[-dd[ HH:mm:ss[.SSS]]]]"

  def zonedDateTimePatterns: Seq[String] = Seq(
    "yyyy-MM[-dd[ HH:mm:ss[.SSS]]]Z",
    "yyyy-MM[-dd[ HH:mm:ss[.SSS]]] z"
  )

  def zonedPatterns = DateTimeFormatter.ofPattern(
    zonedDateTimePatterns.map(s => s"[$s]").mkString("")
  )

  def parseLocalDateTime(s: String, zone: ZoneOffset): Option[ZonedDateTime] = {
    val p = DateTimeFormatter.ofPattern(localDateTimePattern)
    Try(p.parseBest(s, LocalDateTime.from(_), LocalDate.from(_))) match {
      case Success(t) => {
        t match {
          case d: LocalDateTime =>
            Some(ZonedDateTime.of(d, zone))
          case d: LocalDate =>
            Some(d.atStartOfDay(zone))
          case other =>
            None
        }
      }
      case Failure(e) =>
        None
    }
  }

  def parse(s: String, zone: ZoneOffset): Option[ZonedDateTime] = {
    parseLocalDateTime(s, zone)
    .orElse {
      Try(java.time.ZonedDateTime.parse(s, zonedPatterns)).toOption
    }
  }
}
