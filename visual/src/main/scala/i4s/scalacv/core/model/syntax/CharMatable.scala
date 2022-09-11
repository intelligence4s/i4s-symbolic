package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable}
import i4s.scalacv.core.types.Types
import org.bytedeco.javacpp.indexer.{Indexer, CharIndexer}

trait CharMatable extends Matable[Char] {
  override def get(mat: Mat[Char], indices: Int*)(implicit indexer: Indexer): Char = indexer.asInstanceOf[CharIndexer].get(indices.map(_.toLong): _*)
  override def getN(mat: Mat[Char], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Char] = {
    val buffer = new Array[Char](n)
    indexer.asInstanceOf[CharIndexer].get(indices.map(_.toLong).toArray, buffer)
    buffer.toIndexedSeq
  }

  override def put(mat: Mat[Char], indices: Seq[Int], value: Char)(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[CharIndexer].put(indices.toArray.map(_.toLong),value)

  override def putN(mat: Mat[Char], indices: Seq[Int], values: Seq[Char])(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[CharIndexer].put(indices.toArray.map(_.toLong), values.toArray,0,values.size)
}
