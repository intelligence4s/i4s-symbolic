package i4s.scalacv.core.model.mats

import i4s.scalacv.core.types.Types.Type

import scala.reflect.ClassTag

trait BaseMatable[T <: AnyVal] {
  def channels: Int = 1
  def depth: Type

  def indexer(mat: BaseMat[T])(implicit tag: ClassTag[T]): Indexable[T] = Indexable[T](mat.createIndexer())
}
