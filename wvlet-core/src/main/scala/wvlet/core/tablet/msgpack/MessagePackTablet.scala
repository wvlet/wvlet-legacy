package wvlet.core.tablet.msgpack

import java.io.{FileInputStream, FileOutputStream}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import org.msgpack.core.MessagePack
import wvlet.core.tablet.{TabletReader, TabletWriter}

/**
  *
  */
object MessagePackTablet {

  def msgpackGzReader(file:String) : TabletReader = {
    new MessagePackArraySeqReader(MessagePack.newDefaultUnpacker(new GZIPInputStream(new FileInputStream(file))))
  }

  def msgpackGzWriter(file:String) : TabletWriter[Unit] = {
    new MessagePackArraySeqWriter(MessagePack.newDefaultPacker(new GZIPOutputStream(new FileOutputStream(file))))
  }

}
