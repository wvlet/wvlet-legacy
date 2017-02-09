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

import org.msgpack.core.MessagePack
import wvlet.core.tablet._
import wvlet.log.LogSupport
import wvlet.obj.{ObjectSchema, Primitive, TextType, TypeUtil}

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
      val packer = MessagePack.newDefaultBufferPacker()

      if (record == null) {
        packer.packArrayHeader(0) // empty array
      }
      else {
        // TODO polymorphic types (e.g., B extends A, C extends B)
        val objSchema = ObjectSchema(record.getClass)
        //val arrSize = Math.max(objSchema.parameters.length, schema.size)
        // TODO add parameter values not in the schema
        info(objSchema)

        packer.packArrayHeader(objSchema.parameters.length)
        for (p <- objSchema.parameters) {
          val v = p.get(record)
          if (v == null) {
            packer.packNil()
          }
          else {
            p.valueType match {
              case Primitive.Byte | Primitive.Short | Primitive.Int | Primitive.Long =>
                packer.packLong(v.toString.toLong)
              case Primitive.Float | Primitive.Double =>
                packer.packDouble(v.toString.toDouble)
              case Primitive.Boolean =>
                packer.packBoolean(v.toString.toBoolean)
              case Primitive.Char | TextType.String | TextType.File | TextType.Date =>
                packer.packString(v.toString)
              case arr if TypeUtil.isArray(v.getClass) =>
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
                    throw new UnsupportedOperationException(s"Reading array type of ${arr.getClass} is not supported")
                }
              case seq: Seq[_] =>
                packer.packArrayHeader(seq.length)
                for (s <- seq) {
                  packer.packString("test")
                }
              case other =>
                // TODO support Array, Map, etc.
                packer.packString(other.toString())
            }
          }
        }
      }
      Some(MessagePackRecord(packer.toByteArray))
    }
  }
}
