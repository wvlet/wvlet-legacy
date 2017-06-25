package wvlet.core.scales

import java.time.{LocalDateTime, ZonedDateTime}
import java.time.format.DateTimeFormatter

/**
  *
  */
object DateTimeParser {

  def dateTimePatterns: Seq[String] = Seq(
    "yyyy-MM[-dd[ HH:mm:ss[Z]]]",
    "yyyy-MM-dd HH:mm:ss[.SSS]",
    "yyyy-MM-dd HH:mm:ss z"
  )

  val patterns = DateTimeFormatter.ofPattern(
    dateTimePatterns.map(s => s"[$s]").mkString("")
  )

  def parse(s:String): ZonedDateTime = {
    java.time.ZonedDateTime.parse(s, patterns)
  }
}
