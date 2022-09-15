package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable, Scalar}
import org.bytedeco.javacpp.indexer.{ByteIndexer, Indexer}

/*
trait ScalarMatable[T <: AnyVal] extends Matable[Scalar] {
  override def channels: Int = 4  // Default channel count

  override def get(mat: Mat[Scalar], indices: Int*)(implicit indexer: Indexer): Scalar = {
    val longs = indices.map(_.toLong)

    val ch = mat.channels
    val values = (0 until ch) map(ch => indexer.asInstanceOf[ByteIndexer].get(longs :+ ch.toLong :_*))

    Scalar(values:_*)
  }

  override def getN(mat: Mat[Scalar], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Scalar] = {
    val longs = indices.map(_.toLong)
    val ch = mat.channels()

    val buffer = new Array[T](n*ch)
    indexer.asInstanceOf[ByteIndexer].get(longs.toArray,buffer,0,buffer.length)

    val scalars = buffer.sliding(ch,ch).map(s => Scalar(s:_*))
    scalars.toIndexedSeq
  }

  override def put(mat: Mat[Scalar], indices: Seq[Int], value: Scalar)(implicit indexer: Indexer): Unit = {
    val longs = indices.map(_.toLong)
    val ch = mat.channels()

    val parts = value.asArray.take(ch)
    indexer.asInstanceOf[ByteIndexer].put(longs.toArray,parts,0,parts.length)
  }

  override def putN(mat: Mat[Scalar], indices: Seq[Int], values: Seq[Scalar])(implicit indexer: Indexer): Unit = {
    val longs = indices.map(_.toLong)
    val ch = mat.channels()

    val buffer = values.flatMap(_.asArray.take(ch)).toArray
    indexer.asInstanceOf[ByteIndexer].put(longs.toArray,buffer,0,buffer.length)
  }
}
*/
