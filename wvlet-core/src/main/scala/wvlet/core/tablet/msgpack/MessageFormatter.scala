package wvlet.core.tablet.msgpack

import org.msgpack.core.{MessagePacker, MessageUnpacker}
import org.msgpack.value.{ValueType, Variable}
import wvlet.core.tablet.Schema
import wvlet.core.tablet.Schema.ColumnType

import scala.util.{Failure, Success, Try}

trait MessageFormatter[@specialized(Int, Long, Float, Double, Boolean) A] {
  def pack(p:MessagePacker, v:A)
  def unpack(u:MessageUnpacker, v:ValueHolder) : ValueHolder
}

class ValueHolder {
  private var b : Boolean = false
  private var l : Long = 0L
  private var d : Double = 0d
  private var s : String = ""
  private var o : AnyRef = null

  private var valueType : ColumnType = Schema.NIL

  def isNull : Boolean = valueType == Schema.NIL

  def getLong : Long = l
  def getBoolean : Boolean = b
  def getDouble : Double = d
  def getString : String = s
  def getObject : AnyRef = o

  def setLong(v:Long) : Long = {
    l = v
    valueType = Schema.INTEGER
    v
  }

  def setBoolean(v:Boolean) : Boolean = {
    b = v
    valueType = Schema.BOOLEAN
    v
  }

  def setDouble(v:Double) : Double = {
    d = v
    valueType = Schema.FLOAT
    v
  }

  def setString(v:String) : String = {
    s = v
    valueType = Schema.STRING
    v
  }

  def setObject[A](v:A) : A = {
    o = v.asInstanceOf[AnyRef]
    valueType = Schema.ANY
    v
  }

  def setNull {
    valueType = Schema.NIL
  }
}

object MessageFormatter {

  object IntFormatter extends MessageFormatter[Int] {
    override def pack(p: MessagePacker, v: Int): Unit = {
      p.packInt(v)
    }

    override def unpack(u: MessageUnpacker, v:ValueHolder): ValueHolder = {
      val f = u.getNextFormat
      val vt = f.getValueType
      vt match {
        case ValueType.NIL =>
          u.unpackNil()
          v.setNull
        case ValueType.INTEGER =>
          v.setLong(u.unpackLong())
        case ValueType.STRING =>
          // TODO NumberFormat
          Try(u.unpackString.toInt) match {
            case Success(l) =>
              v.setLong(l)
            case Failure(e) =>
              v.setNull
          }
        case ValueType.BOOLEAN =>
          v.setLong(if(u.unpackBoolean()) 1 else 0)
        case ValueType.FLOAT =>
          v.setLong(u.unpackDouble().toLong)
        case ValueType.ARRAY | ValueType.MAP | ValueType.BINARY | ValueType.EXTENSION =>
          u.skipValue()
          v.setNull
      }
      v
    }
  }

  object StringFormatter extends MessageFormatter[String] {
    override def pack(p: MessagePacker, v: String): Unit = {
      p.packString(v)
    }

    override def unpack(u: MessageUnpacker, v: ValueHolder): ValueHolder = {
      u.getNextFormat.getValueType match {
        case ValueType.NIL =>
          u.unpackNil()
          v.setNull
        case ValueType.STRING =>
          v.setString(u.unpackString())
        case ValueType.INTEGER =>
          v.setString(u.unpackLong().toString)
        case ValueType.BOOLEAN =>
          v.setString(u.unpackBoolean().toString)

      }
      v
    }
  }


}