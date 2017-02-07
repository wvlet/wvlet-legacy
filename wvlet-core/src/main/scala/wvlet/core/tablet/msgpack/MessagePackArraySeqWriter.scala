package wvlet.core.tablet.msgpack

import java.io.FileOutputStream
import java.util.zip.GZIPOutputStream

import org.msgpack.core.{MessagePack, MessagePacker}
import wvlet.core.tablet.{ArrayValueRecord, MessagePackRecord, Record, TabletWriter}

object MessgePackArray {
  def msgpackGzWriter(file: String): MessagePackArraySeqWriter = {
    new MessagePackArraySeqWriter(MessagePack.newDefaultPacker(new GZIPOutputStream(new FileOutputStream(file))))
  }
}

/**
  *
  */
class MessagePackArraySeqWriter(packer: MessagePacker) extends TabletWriter {
  def write(r: Record) {
    r match {
      case ArrayValueRecord(v) =>
        packer.packValue(v)
      case MessagePackRecord(arr) =>
        packer.addPayload(arr)
      case other =>
        throw new UnsupportedOperationException(s"Unuspported record type: ${other.getClass}")
    }
  }

  override def close(): Unit = {
    packer.close()
  }
}
