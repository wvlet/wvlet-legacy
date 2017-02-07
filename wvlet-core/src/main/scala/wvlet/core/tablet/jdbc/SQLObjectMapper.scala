package wvlet.core.tablet.jdbc

import java.sql.{Connection, ResultSet}

import wvlet.obj._
import wvlet.log.io.IOUtil._

import scala.reflect.ClassTag

/**
  *
  */
object SQLObjectMapper {

  import scala.reflect.runtime.{universe => ru}

  def sqlTypeOf(tpe: ObjectType): String = {
    tpe match {
      case Primitive.Int => "integer"
      case Primitive.Long => "integer"
      case Primitive.Float => "float"
      case Primitive.Double => "float"
      case Primitive.Boolean => "boolean"
      case TextType.String => "string"
      case TextType.File => "string"
      case TextType.Date => "string"
      case _ =>
        throw new IllegalArgumentException(s"Unknown type ${tpe}")
    }
  }

  def createTableSQLFor[A: ru.TypeTag](tableName: String): String = {
    val schema = ObjectSchema.of[A]
    val params = for (p <- schema.parameters) yield {
      s"${p.name} ${sqlTypeOf(p.valueType)}"
    }
    s"create table if not exists ${tableName} (${params.mkString(", ")})"
  }

  def quote(s: String) = s"'${s}'"

  def insertRecord[A: ru.TypeTag](obj: A, tableName: String, conn: Connection) {
    val schema = ObjectSchema.of[A]
    val colSize = schema.parameters.size
    val tuple = ("?" * colSize).mkString(", ")
    withResource(conn.prepareStatement(s"insert into ${tableName} values(${tuple})")) {prep =>
      for ((p, i) <- schema.parameters.zipWithIndex) yield {
        val v = p.get(obj)
        prep.setObject(i + 1, v)
      }
      prep.execute()
    }
  }

  def readAs[A: ClassTag](rs: ResultSet): A = {
    val cl = implicitly[ClassTag[A]].runtimeClass
    val metadata = rs.getMetaData
    val cols = metadata.getColumnCount
    val b = ObjectBuilder(cl)
    for (i <- 1 to cols) {
      val colName = metadata.getColumnName(i)
      b.set(colName, rs.getObject(i))
    }
    b.build.asInstanceOf[A]
  }
}
