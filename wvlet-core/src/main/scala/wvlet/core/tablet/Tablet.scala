package wvlet.core.tablet

import java.nio.ByteBuffer

import org.msgpack.value.{ArrayValue, MapValue, Value}
import wvlet.core.time.TimeStamp

/**
  *
  */
trait TabletReader {

  def isNull : Boolean
  def readNull : Unit
  def readLong : Long
  def readDouble : Double
  def readString : String
  def readBinary : Array[Byte]
  def readTimestamp : TimeStamp
  def readJson : Value

  def readArray: ArrayValue
  def readMap: MapValue
}

trait TabletWriter { self =>

  private implicit class RichColumnIndex(columnIndex: Int) {
    def toType: Column = schema.columnType(columnIndex)
  }

  def schema: Schema

  // Primitive types
  def writeInteger(c: Column, v: Integer): self.type
  def writeFloat(c: Column, v: Float): self.type
  def writeBoolean(c: Column, v: Boolean): self.type
  def writeString(c: Column, v: String): self.type
  def writeTimeStamp(c: Column, v: TimeStamp): self.type
  def writeBinary(c: Column, v: Array[Byte]): self.type
  def writeBinary(c: Column, v: ByteBuffer): self.type

  // Complex types
  def writeJSON(c: Column, v: Value): self.type
  def writeArray(c: Column, v: ArrayValue): self.type
  def writeMap(c: Column, v: MapValue): self.type

  // Helper methods for column index (0-origin) based accesses
  def writeInteger(columIndex: Int, v: Integer): self.type = writeInteger(columIndex.toType, v)
  def writeFloat(columnIndex: Int, v: Float): self.type = writeFloat(columnIndex.toType, v)
  def writeBoolean(columnIndex: Int, v: Boolean): self.type = writeBoolean(columnIndex.toType, v)
  def writeString(columnIndex: Int, v: String): self.type = writeString(columnIndex.toType, v)
  def writeTimeStamp(columnIndex: Int, v: TimeStamp): self.type = writeTimeStamp(columnIndex.toType, v)
  def writeBinary(columnIndex: Int, v: Array[Byte]): self.type = writeBinary(columnIndex.toType, v)
  def writeBinary(columnIndex: Int, v: ByteBuffer): self.type = writeBinary(columnIndex.toType, v)
  def writeJSON(columnIndex: Int, v: Value): self.type = writeJSON(columnIndex.toType, v)
  def writeArray(columnIndex: Int, v: ArrayValue): self.type = writeArray(columnIndex.toType, v)
  def writeMap(columnIndex: Int, v: MapValue): self.type = writeMap(columnIndex.toType, v)

}

