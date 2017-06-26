package wvlet.core.scales

import wvlet.test.WvletSpec

/**
  *
  */
class TimeWindowTest extends WvletSpec {

  val t = TimeWindow.ofSystem
  info(s"now: ${t.now}")

  def parse(s:String): TimeWindow = {
    val w = t.parse(s)
    info(s"str:${s}, window:${w}")
    w
  }

  "TimeWindow" should {

    "parse string repl" in {
      // duration/offset

      // DURATION := (+ | -)?(INTEGER)(UNIT)
      // UNIT     := s | m | h | d | w | M | y
      //
      // OFFSET   := DURATION | DATE_TIME
      // RANGE    := (DURATION) (/ (OFFSET))?
      // DATE_TIME := yyyy-MM-dd( HH:mm:ss(.ZZZ| ' ' z)?)?
      //

      // The default offset is 0(UNIT) (the beginning of the given time unit)

      // 7 days ago until at the beginning of today.
      // 0d := the beginning of the day
      // [-7d, 0d)
      parse("7d")

      // [-7d, 0d)
      // |-------------|
      // -7d -- ... -- 0d ---- now  ------
      parse("-7d")

      // [0d, +7d)
      //      |------------------------------|
      // ---  0d --- now --- 1d ---  ... --- 7d
      parse("+7d")

      // Since 7 days ago + time fragment from [-7d, now)
      //  |-------------------|
      // -7d - ... - 0d ---- now  ------
      parse("7d/now")

      // [now, +7d)
      //         |---------------------|
      // 0d --- now --- 1d ---  ... --- 7d
      parse("+7d/now")

      // [-1h, 0h)
      parse("1h")
      // [-1h, now)
      parse("1h/now")


      // -12hU:now  (last 12 hours + fraction until now. U is effective only for negative duration)
      // -12hF:now  (-12 hours from now. No trunctation)
      // +12hF:now  (+12 hours from now)

      parse("-12h/now")
      parse("-12h")
      parse("-12h/now")
      parse("+12h/now")

      // Absolute offset
      // 3d:2017-04-07 [2017-04-04,2017-04-07)
      parse("3d/2017-04-07")

      // The offset can be specified using a duration
      // -1M:-1M  [2017-04-01, 2017-05-01) if today is 2017-05-20
      parse("1M/0M")
      // -1M:-1M  [2017-03-01, 2017-04-01) if today is 2017-05-20
      parse("1M/1M")

      // -1h/2017-01-23 01:00:00 -> [2017-01-23 00:00:00,2017-01-23 01:00:00]
      // -1h/2017-01-23 01:23:45 -> [2017-01-23 00:00:00,2017-01-23 01:23:45]
      // 60m/2017-01-23 01:23:45 -> [2017-01-23 00:23:45,2017-01-23 01:23:45]
      parse("-1h/2017-01-23 01:00:00")
      parse("-1h/2017-01-23 01:23:45")
      parse("60m/2017-01-23 01:23:45")
    }
  }

}
