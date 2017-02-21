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
import wvlet.core.tablet.msgpack.{MessageFormatter, MessageHolder}
import wvlet.log.LogSupport
import wvlet.obj.{GenericType, _}

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}

class ObjectInput(codec: Map[Class[_], MessageFormatter[_]] = Map.empty) extends LogSupport {

  def packValue(packer: MessagePacker, v: Any, valueType: ObjectType) {
    trace(s"packValue: ${v}, ${valueType}, ${valueType.getClass}")
    if (v == null) {
      packer.packNil()
    }
    else if (codec.contains(valueType.rawType)) {
      codec(valueType.rawType).asInstanceOf[MessageFormatter[Any]].pack(packer, v)
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
          if (opt.isEmpty) {
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
          for ((k, v) <- m.seq) {
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

  def packObj(packer: MessagePacker, obj: Any) {
    val cl = obj.getClass
    if (codec.contains(cl)) {
      trace(s"Using codec for ${cl}")
      codec(cl).asInstanceOf[MessageFormatter[Any]].pack(packer, obj)
    }
    else {
      val valueType = ObjectType.of(cl)
      if (valueType.isPrimitive) {
        packValue(packer, obj, valueType)
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
  }

  def read[A](record: A): Record = {
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


