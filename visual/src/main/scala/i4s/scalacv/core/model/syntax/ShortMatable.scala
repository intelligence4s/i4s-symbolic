package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable}
import i4s.scalacv.core.types.Types
import org.bytedeco.javacpp.indexer.{Indexer, ShortIndexer}

trait ShortMatable extends Matable[Short] {
  override def get(mat: Mat[Short], indices: Int*)(implicit indexer: Indexer): Short = indexer.asInstanceOf[ShortIndexer].get(indices.map(_.toLong): _*)
  override def getN(mat: Mat[Short], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Short] = {
    val buffer = new Array[Short](n)
    indexer.asInstanceOf[ShortIndexer].get(indices.map(_.toLong).toArray, buffer)
    buffer.toIndexedSeq
  }

  override def put(mat: Mat[Short], indices: Seq[Int], value: Short)(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[ShortIndexer].put(indices.toArray.map(_.toLong), value)

  override def putN(mat: Mat[Short], indices: Seq[Int], values: Seq[Short])(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[ShortIndexer].put(indices.toArray.map(_.toLong),values.toArray,0,values.size)
}
