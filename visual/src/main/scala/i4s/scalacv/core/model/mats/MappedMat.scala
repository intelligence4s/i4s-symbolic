package i4s.scalacv.core.model.mats

import i4s.scalacv.core.model.Math.NumberLike
import i4s.scalacv.core.model.Scalar
import i4s.scalacv.core.types.Types.Type

import scala.reflect.ClassTag

object MappedMat {
  def apply[M, T <: AnyVal](rows: Int)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M, T] = new MappedMat[M, T](None,rows)
  def apply[M, T <: AnyVal](rows: Int, depth: Option[Type], ch: Option[Int])(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,rows)
  def apply[M, T <: AnyVal](rows: Int, depth: Option[Type], ch: Option[Int], init: Scalar)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,init,rows)

  def apply[M, T <: AnyVal](rows: Int, cols: Int)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,rows,Seq(cols):_*)
  def apply[M, T <: AnyVal](rows: Int, cols: Int, init: Scalar)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,init,rows,Seq(cols):_*)
  def apply[M, T <: AnyVal](rows: Int, cols: Int, depth: Option[Type], ch: Option[Int])(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,rows,Seq(cols):_*)
  def apply[M, T <: AnyVal](rows: Int, cols: Int, depth: Option[Type], ch: Option[Int], init: Scalar)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,init,rows,Seq(cols):_*)

  def apply[M, T <: AnyVal](dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,dim1,dims:_*)
  def apply[M, T <: AnyVal](init: Scalar,dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,init,dim1,dims:_*)
  def apply[M, T <: AnyVal](depth: Option[Type], ch: Option[Int], dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,dim1,dims:_*)
  def apply[M, T <: AnyVal](depth: Option[Type], ch: Option[Int], init: Scalar, dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,init,dim1,dims:_*)
 }

class MappedMat[M : ClassTag, T <: AnyVal : ClassTag : NumberLike](depth: Option[Type], channels: Int, dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T]) extends Mat[T](depth,channels,dim1,dims:_*)
{
  def this(depth: Option[Type], ch: Option[Int], r: Int)(implicit mm: MappedMatable[M, T]) = this(depth,ch.getOrElse(mm.channels),r,Nil :_*)
  def this(depth: Option[Type], ch: Option[Int], init: Scalar, r: Int)(implicit mm: MappedMatable[M, T]) = {
    this(depth,ch.getOrElse(mm.channels),r,Nil:_*)
    put(init)
  }

  def this(depth: Option[Type], ch: Option[Int], d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T]) = this(depth,ch.getOrElse(mm.channels),d1,ds:_*)
  def this(depth: Option[Type], ch: Option[Int], init: Scalar, d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T]) = {
    this(depth,ch.getOrElse(mm.channels),d1,ds:_*)
    put(init)
  }

  def this(depth: Option[Type], r: Int)(implicit mm: MappedMatable[M, T]) = this(depth,mm.channels,r,Nil:_*)
  def this(depth: Option[Type], init: Scalar, r: Int)(implicit mm: MappedMatable[M, T]) = {
    this(depth,mm.channels,r,Nil:_*)
    put(init)
  }

  def this(depth: Option[Type], d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T]) = this(depth,mm.channels,d1,ds:_*)
  def this(depth: Option[Type], init: Scalar, d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T]) = {
    this(depth,mm.channels,d1,ds:_*)
    put(init)
  }

  def getMapped(i: Int): M = mm.getMapped(this,i)
  def getMapped(i: Int, is: Int*): M = mm.getMapped(this, i +: is:_*)

  def getMappedN(n: Int, i: Int): IndexedSeq[M] = mm.getMappedN(this,Array(i),n)
  def getMappedN(n: Int, i: Int, is: Int*): IndexedSeq[M] = mm.getMappedN(this,i +: is,n)

  def putMapped(i: Int, value: M): Unit = mm.putMapped(this,Array(i),value)
  def putMapped(i: Int, j: Int, value: M): Unit = mm.putMapped(this,Array(i,j),value)
  def putMapped(i: Int, j: Int, k: Int, value: M): Unit = mm.putMapped(this,Array(i,j,k),value)
  def putMapped(indices: IndexedSeq[Int], value: M): Unit = mm.putMapped(this,indices,value)

  def putT1Mapped(values: (Int, M)*): Unit = values.foreach { case (i, v) => putMapped(i, v) }
  def putT2Mapped(values: (Int, Int, M)*): Unit = values.foreach { case (i, j, v) => putMapped(i, j, v) }
  def putT3Mapped(values: (Int, Int, Int, M)*): Unit = values.foreach { case (i, j, k, v) => putMapped(i, j, k, v) }

  def putAllMapped(values: Seq[M]): Unit = mm.putMappedN(this,Array(0),values)
  def putAllMapped(i: Int, values: Seq[M]): Unit = mm.putMappedN(this,Array(i),values)
  def putAllMapped(i: Int, j: Int, values: Seq[M]): Unit = mm.putMappedN(this,Array(i,j),values)
}
