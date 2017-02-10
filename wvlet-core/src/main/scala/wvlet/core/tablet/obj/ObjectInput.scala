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

import org.msgpack.core.{MessagePack, MessagePacker}
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

class ObjectWriter[A](cl:Class[A]) extends TabletWriter[A] with LogSupport {

  private val schema = ObjectSchema(cl)
  private lazy val paramIndex = (for((p, i) <- schema.parameters.zipWithIndex) yield {
    i -> p
  }).toMap

  override def write(record: Record): A = {
    val unpacker = record.unpacker
    var index : Int = 0
    val cols = unpacker.unpackArrayHeader()

    val args = Array.newBuilder[AnyRef]
    while(index < cols && unpacker.hasNext) {

      val f = unpacker.getNextFormat
      f.getValueType match {
        case ValueType.NIL =>
          val param = paramIndex(index)
          unpacker.unpackNil()
          args += TypeUtil.zero(param.rawType, param.valueType).asInstanceOf[AnyRef]
        case ValueType.BOOLEAN =>
          args += java.lang.Boolean.valueOf(unpacker.unpackBoolean())
        case ValueType.INTEGER =>
          args += java.lang.Long.valueOf(unpacker.unpackLong())
        case ValueType.FLOAT =>
          args += java.lang.Double.valueOf(unpacker.unpackDouble())
        case ValueType.STRING =>
          args += unpacker.unpackString()
        case ValueType.BINARY =>
          val size = unpacker.unpackBinaryHeader()
          args += unpacker.readPayload(size)
        case ValueType.ARRAY =>
          args += unpacker.unpackValue()
        case ValueType.MAP =>
          args += unpacker.unpackValue()
        case ValueType.EXTENSION =>
          args += unpacker.unpackValue()
      }
      index += 1
    }

    val a = args.result()
    info(s"${a.mkString(", ")}")
    schema.constructor.newInstance(a).asInstanceOf[A]
  }

  override def close(): Unit = {}
}



object ObjectInput extends LogSupport {

  def packValue(packer:MessagePacker, v:Any, valueType:ObjectType) {
    trace(s"packValue: ${v}, ${valueType}, ${valueType.getClass}")
    if (v == null) {
      packer.packNil()
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
            packValue(packer, content, ObjectType.of(content.getClass))
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
class ObjectTabletReader[A](input:Seq[A]) extends TabletReader with LogSupport {

  private val cursor = input.iterator

  def read: Option[Record] = {
    if (!cursor.hasNext) {
      None
    }
    else {

      val record = cursor.next()
      Some(ObjectInput.read(record))
    }
  }
}
