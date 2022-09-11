package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable}
import i4s.scalacv.core.types.Types
import org.bytedeco.javacpp.indexer.{DoubleIndexer, Indexer}

trait DoubleMatable extends Matable[Double] {
  override def get(mat: Mat[Double], indices: Int*)(implicit indexer: Indexer): Double = indexer.asInstanceOf[DoubleIndexer].get(indices.map(_.toLong): _*)
  override def getN(mat: Mat[Double], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Double] = {
    val buffer = new Array[Double](n)
    indexer.asInstanceOf[DoubleIndexer].get(indices.map(_.toLong).toArray, buffer)
    buffer.toIndexedSeq
  }

  override def put(mat: Mat[Double], indices: Seq[Int], value: Double)(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[DoubleIndexer].put(indices.toArray.map(_.toLong),value)

  override def putN(mat: Mat[Double], indices: Seq[Int], values: Seq[Double])(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[DoubleIndexer].put(indices.toArray.map(_.toLong),values.toArray,0,values.size)

}
