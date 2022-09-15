package i4s.scalacv.core.model.mats

import scala.reflect.ClassTag

trait PrimitiveMatable[T <: AnyVal] extends Matable[T] {
  override def get(mat: Mat[T], indices: Int*)(implicit indexer: Indexable[T]): T = indexer.get(indices)
  override def getN(mat: Mat[T], indices: Seq[Int], n: Int)(implicit indexer: Indexable[T], tag: ClassTag[T]): IndexedSeq[T] = {
      val buffer = new Array[T](n)
      indexer.get(indices,buffer)
      buffer.toIndexedSeq
  }

  override def put(mat: Mat[T], indices: Seq[Int], value: T)(implicit indexer: Indexable[T]): Unit = indexer.put(indices,value)
  override def putN(mat: Mat[T], indices: Seq[Int], values: Seq[T])(implicit indexer: Indexable[T]): Unit = indexer.put(indices,values)
}
