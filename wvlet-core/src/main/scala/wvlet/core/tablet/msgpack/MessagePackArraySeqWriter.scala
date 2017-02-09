package wvlet.core.tablet.msgpack

import java.io.FileOutputStream
import java.util.zip.GZIPOutputStream

import org.msgpack.core.{MessagePack, MessagePacker}
import wvlet.core.tablet._

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
    r.pack(packer)
  }

  override def close(): Unit = {
    packer.close()
  }
}
