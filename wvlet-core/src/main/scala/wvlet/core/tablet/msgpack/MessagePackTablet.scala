package wvlet.core.tablet.msgpack

import java.io.{FileInputStream, FileOutputStream}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import org.msgpack.core.{MessagePack, MessagePacker, MessageUnpacker}
import wvlet.core.tablet.{MessagePackRecord, Record, TabletReader, TabletWriter}
import wvlet.log.LogSupport

/**
  *
  */
object MessagePackTablet {

  def msgpackGzReader(file:String) : TabletReader = {
    new MessagePackTabletReader(MessagePack.newDefaultUnpacker(new GZIPInputStream(new FileInputStream(file))))
  }

  def msgpackGzWriter(file:String) : TabletWriter[Unit] = {
    new MessagePackTabletWriter(MessagePack.newDefaultPacker(new GZIPOutputStream(new FileOutputStream(file))))
  }

}

/**
  *
  */
class MessagePackTabletReader(unpacker:MessageUnpacker) extends TabletReader with LogSupport {

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


/**
  *
  */
class MessagePackTabletWriter(packer: MessagePacker) extends TabletWriter[Unit] {
  def write(r: Record) {
    r.pack(packer)
  }

  override def close(): Unit = {
    packer.close()
  }
}
