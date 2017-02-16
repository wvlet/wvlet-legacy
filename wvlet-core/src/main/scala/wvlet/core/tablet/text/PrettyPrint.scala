package wvlet.core.tablet.text

import org.msgpack.value.Variable
import wvlet.core.tablet.obj.ObjectTabletReader
import wvlet.log.LogSupport
import wvlet.obj.ObjectSchema

/**
  *
  */
object PrettyPrint extends LogSupport {

  def show[A](seq:Seq[A], limit:Int=20) {
    pp(seq.take(limit))
  }

  def pp[A](seq:Seq[A]) {
    info(pf(seq).mkString("\n"))
  }

  def pf[A](seq:Seq[A]) : Seq[String] = {
    val b = Seq.newBuilder[Seq[String]]
    val paramNames = seq.headOption.map { x =>
      val schema = ObjectSchema(x.getClass)
      b += schema.parameters.map(_.name).toSeq
    }

    val reader = new ObjectTabletReader(seq)
    b ++= (reader | RecordPrinter)
    val rows = b.result
    val colWidth = maxColWidths(rows)
    val s = Seq.newBuilder[String]
    for(r <- rows) {
      val cols = for((c, i) <- r.zipWithIndex) yield pad(c, colWidth(i))
      s += cols.mkString(" ")
    }
    s.result()
  }



  def maxColWidths(rows:Seq[Seq[String]]) : IndexedSeq[Int] = {
    val maxWidth = (0 until rows.length).map(i => 0).toArray
    for(r <- rows; (c, i) <- r.zipWithIndex) {
      maxWidth(i) = math.max(c.length, maxWidth(i))
    }
    maxWidth.toIndexedSeq
  }

  def pad(s:String, colWidth:Int) : String = {
    (" " * Math.max(0, colWidth - s.length)) + s
  }

}

