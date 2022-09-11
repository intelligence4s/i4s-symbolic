package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Matable, Mat}
import i4s.scalacv.core.types.Types
import org.bytedeco.javacpp.indexer.{FloatIndexer, Indexer}

trait FloatMatable extends Matable[Float] {
  override def get(mat: Mat[Float], indices: Int*)(implicit indexer: Indexer): Float = indexer.asInstanceOf[FloatIndexer].get(indices.map(_.toLong): _*)
  override def getN(mat: Mat[Float], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Float] = {
    val buffer = new Array[Float](n)
    indexer.asInstanceOf[FloatIndexer].get(indices.map(_.toLong).toArray, buffer)
    buffer.toIndexedSeq
  }

  override def put(mat: Mat[Float], indices: Seq[Int], value: Float)(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[FloatIndexer].put(indices.toArray.map(_.toLong), value)

  override def putN(mat: Mat[Float], indices: Seq[Int], values: Seq[Float])(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[FloatIndexer].put(indices.toArray.map(_.toLong),values.toArray,0,values.size)
}
