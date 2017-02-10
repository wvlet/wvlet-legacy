/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wvlet.core.tablet.obj

import org.msgpack.core.{MessagePack, MessagePacker, MessageUnpacker}
import org.msgpack.value.ValueType
import wvlet.core.tablet._
import wvlet.log.LogSupport
import wvlet.obj.{GenericType, _}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

object ObjectWriter {

  def createScheamOf[A: ru.TypeTag](name: String): Schema = {
    val schema = ObjectSchema.of[A]
    val tabletColumnTypes: Seq[Column] = for ((p, i) <- schema.parameters.zipWithIndex) yield {
      val vt = p.valueType
      val columnType: Schema.ColumnType = vt match {
        case Primitive.Byte => Schema.INTEGER
        case Primitive.Short => Schema.INTEGER
        case Primitive.Int => Schema.INTEGER
        case Primitive.Long => Schema.INTEGER
        case Primitive.Float => Schema.FLOAT
        case Primitive.Double => Schema.FLOAT
        case Primitive.Char => Schema.STRING
        case Primitive.Boolean => Schema.BOOLEAN
        case TextType.String => Schema.STRING
        case TextType.File => Schema.STRING
        case TextType.Date => Schema.STRING
        case _ =>
          // TODO support Option, Array, Map, the other types etc.
          Schema.STRING
      }
      Column(i, p.name, columnType)
    }
    Schema(name, tabletColumnTypes)
  }

  def of[A:ClassTag] : ObjectWriter[A] = {
    val cl = implicitly[ClassTag[A]]
    new ObjectWriter(cl.runtimeClass.asInstanceOf[Class[A]])
  }
}

trait MessagePackCodec[A] {
  def packTo(a:A, packer:MessagePacker)
  def unpack(unpacker:MessageUnpacker) : A
}


class ObjectWriter[A](cl:Class[A], codec:Map[Class[_], MessagePackCodec[_]] = Map.empty) extends TabletWriter[A] with LogSupport {

  private def unpack(unpacker:MessageUnpacker, objType:ObjectType) : AnyRef = {
    objType match {
      case AliasedObjectType(_, _, orig) =>
        unpack(unpacker, orig)
      case OptionType(cl, elemType) =>
        if(unpacker.getNextFormat.getValueType.isNilType) {
          unpacker.unpackNil()
          None
        }
        else {
          Some(unpack(unpacker, elemType))
        }
      case _ =>
        val f = unpacker.getNextFormat
        val vt = f.getValueType
        val readValue =
          if (!vt.isNilType && codec.contains(objType.rawType)) {
            codec(objType.rawType).unpack(unpacker).asInstanceOf[AnyRef]
          }
          else {
            vt match {
              case ValueType.NIL =>
                unpacker.unpackNil()
                TypeUtil.zero(objType.rawType, objType)
              case ValueType.BOOLEAN =>
                java.lang.Boolean.valueOf(unpacker.unpackBoolean())
              case ValueType.INTEGER =>
                val l = unpacker.unpackLong()
                objType match {
                  case Primitive.Byte =>
                    java.lang.Byte.valueOf(l.toByte)
                  case Primitive.Short =>
                    java.lang.Short.valueOf(l.toShort)
                  case Primitive.Int =>
                    java.lang.Integer.valueOf(l.toInt)
                  case Primitive.Long =>
                    java.lang.Long.valueOf(l)
                }
              case ValueType.FLOAT =>
                java.lang.Double.valueOf(unpacker.unpackDouble())
              case ValueType.STRING =>
                unpacker.unpackString()
              case ValueType.BINARY =>
                val size = unpacker.unpackBinaryHeader()
                unpacker.readPayload(size)
              case ValueType.ARRAY =>
                objType match {
                  case SeqType(cl, elemType) =>
                    val b = Seq.newBuilder[Any]
                    val size = unpacker.unpackArrayHeader()
                    (0 until size).foreach { i =>
                      b += unpack(unpacker, elemType)
                    }
                    b.result()
                  case _ =>
                    unpackObj(unpacker, objType)
                }
              case ValueType.MAP =>
                // TODO? support mapping key->value to obj?
                objType match {
                  case MapType(cl, keyType, valueType) =>
                    val b = Map.newBuilder[Any, Any]
                    val size = unpacker.unpackMapHeader()
                    (0 until size).foreach {i =>
                      val k = unpack(unpacker, keyType)
                      val v = unpack(unpacker, valueType)
                      b += (k -> v)
                    }
                    b.result()
                  case _ =>
                    // Fallback to JSOn
                    val v = unpacker.unpackValue()
                    v.toJson
                }
              case ValueType.EXTENSION =>
                warn(s"Extension type mapping is not properly supported")
                // Fallback to JSON
                unpacker.unpackValue().toJson
            }
          }
        readValue.asInstanceOf[AnyRef]
    }
  }

  private def unpackObj(unpacker:MessageUnpacker, objType:ObjectType) : AnyRef = {
    val schema = ObjectSchema.of(objType)
    val f = unpacker.getNextFormat
    trace(s"unpack obj ${f} -> ${objType}")
    if(codec.contains(objType.rawType)) {
      if(f.getValueType == ValueType.NIL) {
        unpacker.unpackNil()
        null
      }
      else {
        codec(objType.rawType).unpack(unpacker).asInstanceOf[AnyRef]
      }
    }
    else {
      val size = unpacker.unpackArrayHeader()
      var index = 0
      val args = Array.newBuilder[AnyRef]
      val params = schema.parameters
      while(index < size) {
        args += unpack(unpacker, params(index).valueType)
        index += 1
      }
      val a = args.result()
      trace(s"${a.map(x => s"${x.getClass.getName}:${x}").mkString("\n")}")
      val r = schema.constructor.newInstance(a).asInstanceOf[AnyRef]
      trace(r)
      r
    }
  }

  override def write(record: Record): A = {
    val unpacker = record.unpacker
    unpackObj(unpacker, ObjectType.of(cl)).asInstanceOf[A]
  }

  override def close(): Unit = {}
}



class ObjectInput(codec:Map[Class[_], MessagePackCodec[_]] = Map.empty) extends LogSupport {

  def packValue(packer:MessagePacker, v:Any, valueType:ObjectType) {
    trace(s"packValue: ${v}, ${valueType}, ${valueType.getClass}")
    if (v == null) {
      packer.packNil()
    }
    else if(codec.contains(valueType.rawType)) {
      codec(valueType.rawType).asInstanceOf[MessagePackCodec[Any]].packTo(v, packer)
    }
    else {
      valueType match {
        case Primitive.Byte | Primitive.Short | Primitive.Int | Primitive.Long =>
          packer.packLong(v.toString.toLong)
        case Primitive.Float | Primitive.Double =>
          packer.packDouble(v.toString.toDouble)
        case Primitive.Boolean =>
          packer.packBoolean(v.toString.toBoolean)
        case Primitive.Char | TextType.String | TextType.File | TextType.Date =>
          packer.packString(v.toString)
        case OptionType(cl, elemType) =>
          val opt = v.asInstanceOf[Option[_]]
          if(opt.isEmpty) {
            packer.packNil()
          }
          else {
            val content = opt.get
            packValue(packer, content, elemType)
          }
        case AliasedObjectType(name, fullName, base) =>
          packValue(packer, v, base)
        case MapType(cl, keyType, valueType) =>
          val m = v.asInstanceOf[Map[_, _]]
          packer.packMapHeader(m.size)
          for((k, v) <- m.seq) {
            packValue(packer, k, keyType)
            packValue(packer, v, keyType)
          }
        case ArrayType(cl, elemType) =>
          v match {
            case a: Array[String] =>
              packer.packArrayHeader(a.length)
              a.foreach(packer.packString(_))
            case a: Array[Int] =>
              packer.packArrayHeader(a.length)
              a.foreach(packer.packInt(_))
            case a: Array[Float] =>
              packer.packArrayHeader(a.length)
              a.foreach(packer.packFloat(_))
            case a: Array[Double] =>
              packer.packArrayHeader(a.length)
              a.foreach(packer.packDouble(_))
            case a: Array[Boolean] =>
              packer.packArrayHeader(a.length)
              a.foreach(packer.packBoolean(_))
            case _ =>
              throw new UnsupportedOperationException(s"Reading array type of ${valueType} is not supported: ${v}")
          }
        case SeqType(cl, elemType) =>
          val seq = v.asInstanceOf[Seq[_]]
          packer.packArrayHeader(seq.length)
          for (s <- seq) {
            packValue(packer, s, ObjectType.of(s.getClass))
          }
        case enum if v.getClass.isInstanceOf[Enum[_]] =>
          packer.packString(v.asInstanceOf[Enum[_]].name())
        case other if valueType.name == "DateTime" =>
          // TODO Allow having custom serde
          packer.packString(v.toString())
        case other =>
          // Nested objects
          packObj(packer, v)
      }
    }
  }

  def packObj(packer:MessagePacker, obj:Any) {

    val cl = obj.getClass
    if (codec.contains(cl)) {
      trace(s"Using codec for ${cl}")
      codec(cl).asInstanceOf[MessagePackCodec[Any]].packTo(obj, packer)
    }
    else {
      // TODO polymorphic types (e.g., B extends A, C extends B)
      val objSchema = ObjectSchema(obj.getClass)
      // TODO add parameter values not in the schema
      packer.packArrayHeader(objSchema.parameters.length)
      for (p <- objSchema.parameters) {
        val v = p.get(obj)
        trace(s"packing ${p.name}, ${p.valueType}")
        packValue(packer, v, p.valueType)
      }
    }
  }

  def read[A](record:A) : Record = {
    val packer = MessagePack.newDefaultBufferPacker()
    if (record == null) {
      packer.packArrayHeader(0) // empty array
    }
    else {
      packObj(packer, record)
    }
    MessagePackRecord(packer.toByteArray)
  }
}


/**
  *
  */
class ObjectTabletReader[A](input:Seq[A], codec:Map[Class[_], MessagePackCodec[_]] = Map.empty) extends TabletReader with LogSupport {

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
