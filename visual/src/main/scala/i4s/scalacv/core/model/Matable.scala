package i4s.scalacv.core.model

import i4s.scalacv.core.types.Types.Type

import scala.reflect.ClassTag

trait Matable[T <: AnyVal] {
  def channels: Int = 1
  def depth: Type

  def indexer(mat: Mat[T])(implicit tag: ClassTag[T]): Indexable[T] = Indexable[T](mat.createIndexer())

  def get(mat: Mat[T], indices: Int*)(implicit indexer: Indexable[T]): T
  def getN(mat: Mat[T], indices: Seq[Int], n: Int)(implicit indexer: Indexable[T], tag: ClassTag[T]): IndexedSeq[T]

  def put(mat: Mat[T], indices: Seq[Int], value: T)(implicit indexer: Indexable[T]): Unit
  def putN(mat: Mat[T], indices: Seq[Int], values: Seq[T])(implicit indexer: Indexable[T]): Unit
}
