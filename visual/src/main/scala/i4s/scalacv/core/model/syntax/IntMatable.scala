package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable}
import i4s.scalacv.core.types.Types
import org.bytedeco.javacpp.indexer.{IntIndexer, Indexer}

trait IntMatable extends Matable[Int] {
  override def get(mat: Mat[Int], indices: Int*)(implicit indexer: Indexer): Int = indexer.asInstanceOf[IntIndexer].get(indices.map(_.toLong): _*)
  override def getN(mat: Mat[Int], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Int] = {
    val buffer = new Array[Int](n)
    indexer.asInstanceOf[IntIndexer].get(indices.map(_.toLong).toArray, buffer)
    buffer.toIndexedSeq
  }

  override def put(mat: Mat[Int], indices: Seq[Int], value: Int)(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[IntIndexer].put(indices.toArray.map(_.toLong), value)
  override def putN(mat: Mat[Int], indices: Seq[Int], values: Seq[Int])(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[IntIndexer].put(indices.toArray.map(_.toLong),values.toArray,0,values.size)
}
