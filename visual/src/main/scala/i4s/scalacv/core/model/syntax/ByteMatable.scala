package i4s.scalacv.core.model.syntax

import i4s.scalacv.core.model.{Mat, Matable}
import i4s.scalacv.core.types.Types
import org.bytedeco.javacpp.indexer.{ByteIndexer, Indexer}

trait ByteMatable extends Matable[Byte] {
  override def get(mat: Mat[Byte], indices: Int*)(implicit indexer: Indexer): Byte = indexer.asInstanceOf[ByteIndexer].get(indices.map(_.toLong): _*)
  override def getN(mat: Mat[Byte], indices: Seq[Int], n: Int)(implicit indexer: Indexer): IndexedSeq[Byte] = {
    val buffer = new Array[Byte](n)
    indexer.asInstanceOf[ByteIndexer].get(indices.map(_.toLong).toArray, buffer)
    buffer.toIndexedSeq
  }

  override def put(mat: Mat[Byte], indices: Seq[Int], value: Byte)(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[ByteIndexer].put(indices.toArray.map(_.toLong),value)

  override def putN(mat: Mat[Byte], indices: Seq[Int], values: Seq[Byte])(implicit indexer: Indexer): Unit =
    indexer.asInstanceOf[ByteIndexer].put(indices.toArray.map(_.toLong),values.toArray,0,values.length)

}
