package i4s.scalacv.core.model.mats

import i4s.scalacv.core.model.Math.NumberLike

import scala.reflect.ClassTag

trait MappedMatable[M, T <: AnyVal] extends Matable[T] {
  def getMapped(mat: BaseMat[T], indices: Int*)(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): M
  def getMappedN(mat: BaseMat[T], indices: Seq[Int], n: Int)(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): IndexedSeq[M]
  def putMapped(mat: BaseMat[T], indices: Seq[Int], value: M)(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): Unit
  def putMappedN(mat: BaseMat[T], indices: Seq[Int], values: Seq[M])(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): Unit
}
