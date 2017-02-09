package wvlet.core.tablet.msgpack

import org.msgpack.core.{MessagePack, MessageUnpacker}
import wvlet.core.tablet.{MessagePackRecord, Record, TabletReader}
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
        // TODO read as Array[Byte] without translating it to Value objects
        val packer = MessagePack.newDefaultBufferPacker()
        packer.packValue(v)
        Some(MessagePackRecord(packer.toByteArray))
      }
      else {
        error(s"${v} is not an array")
        // TODO error handling
        throw new IllegalStateException(s"${v} is not an array")
      }
    }
  }
}
