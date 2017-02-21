package wvlet.core.tablet.obj

import wvlet.core.tablet.msgpack.MessageFormatter
import wvlet.core.tablet.{Record, TabletReader}
import wvlet.log.LogSupport

/**
  *
  */
class ObjectTabletReader[A](input: Seq[A], codec: Map[Class[_], MessageFormatter[_]] = Map.empty) extends TabletReader with LogSupport {

  private val cursor = input.iterator

  private val reader = new ObjectInput(codec = codec)

  def read: Option[Record] = {
    if (!cursor.hasNext) {
      None
    }
    else {
      val record = cursor.next()
      Some(reader.read(record))
    }
  }
}
