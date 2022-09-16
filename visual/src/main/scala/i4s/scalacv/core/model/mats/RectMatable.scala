package i4s.scalacv.core.model.mats

import i4s.scalacv.core.model.Math.NumberLike
import i4s.scalacv.core.model.Rect
import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types.Type

import scala.reflect.ClassTag

class RectMatable[T <: AnyVal] extends MappedMatable[Rect,T] {
  override def channels: Int = 4  // Default channel count
  override def depth: Type = Types.Cv32S

  override def getMapped(mat: Mat[T], indices: Int*)(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): Rect = {
    val ch = mat.channels
    val values = getN(mat, indices, ch).map(nl.toInt)
    Rect(values: _*)
  }

  override def getMappedN(mat: Mat[T], indices: Seq[Int], n: Int)(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): IndexedSeq[Rect] = {
    val ch = mat.channels
    val values = getN(mat, indices, ch*n).map(nl.toInt)
    values.sliding(ch,ch).map(s => Rect(s:_*)).toIndexedSeq
  }

  override def putMapped(mat: Mat[T], indices: Seq[Int], value: Rect)(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): Unit = {
    val ch = mat.channels
    val parts = value.asArray.take(ch).map(nl.fromInt)
    putN(mat,indices,parts)
  }

  override def putMappedN(mat: Mat[T], indices: Seq[Int], values: Seq[Rect])(implicit indexer: Indexable[T], nl: NumberLike[T], tag: ClassTag[T]): Unit = {
    val ch = mat.channels
    val expanded = values.flatMap(_.asArray.take(ch).map(nl.fromInt))
    putN(mat,indices,expanded)
  }
}