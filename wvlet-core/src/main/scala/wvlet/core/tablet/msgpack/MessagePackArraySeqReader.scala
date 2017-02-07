package wvlet.core.tablet.msgpack

import org.msgpack.core.MessageUnpacker
import wvlet.core.tablet.{ArrayValueRecord, Record, TabletReader}
import wvlet.log.LogSupport

/**
  *
  */
class MessagePackArraySeqReader(unpacker:MessageUnpacker) extends TabletReader with LogSupport {

  override def read: Option[Record] = {
    if(!unpacker.hasNext) {
      None
    }
    else {
      val v = unpacker.unpackValue()
      if(v.isArrayValue) {
        Some(ArrayValueRecord(v.asArrayValue()))
      }
      else {
        error(s"${v} is not an array")
        // TODO error handling
        throw new IllegalStateException(s"${v} is not an array")
      }
    }
  }
}
