package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable, Scalar4b}
import org.bytedeco.javacpp.indexer.{ByteIndexer, Indexer}

trait ByteScalarMatable extends Matable[Scalar4b] {
  override def channels: Int = 4  // Default channel count

  override def get(mat: Mat[Scalar4b], indices: Int*)(implicit indexer: Indexer): Scalar4b = {
    val longs = indices.map(_.toLong)

    val ch = mat.channels
    val values = (0 until ch) map(ch => indexer.asInstanceOf[ByteIndexer].get(longs :+ ch.toLong :_*))

    Scalar4b(values:_*)
  }

  override def getN(mat: Mat[Scalar4b], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Scalar4b] = {
    val longs = indices.map(_.toLong)
    val ch = mat.channels()

    val buffer = new Array[Byte](n*ch)
    indexer.asInstanceOf[ByteIndexer].get(longs.toArray,buffer,0,buffer.length)

    val scalars = buffer.sliding(ch,ch).map(s => Scalar4b(s:_*))
    scalars.toIndexedSeq
  }

  override def put(mat: Mat[Scalar4b], indices: Seq[Int], value: Scalar4b)(implicit indexer: Indexer): Unit = {
    val longs = indices.map(_.toLong)
    val ch = mat.channels()

    val parts = value.asArray.take(ch)
    indexer.asInstanceOf[ByteIndexer].put(longs.toArray,parts,0,parts.length)
  }

  override def putN(mat: Mat[Scalar4b], indices: Seq[Int], values: Seq[Scalar4b])(implicit indexer: Indexer): Unit = {
    val longs = indices.map(_.toLong)
    val ch = mat.channels()

    val buffer = values.flatMap(_.asArray.take(ch)).toArray
    indexer.asInstanceOf[ByteIndexer].put(longs.toArray,buffer,0,buffer.length)
  }
}
