package i4s.scalacv.core.model.mats

import i4s.scalacv.core.model.Math.NumberLike
import i4s.scalacv.core.model.Scalar
import i4s.scalacv.core.types.Types.Type

import scala.reflect.ClassTag

object MappedMat {
  def apply[M, T <: AnyVal](rows: Int)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M, T] = new MappedMat[M, T](None,rows)
  def apply[M, T <: AnyVal](rows: Int, depth: Option[Type], ch: Option[Int])(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,rows)
  def apply[M, T <: AnyVal](rows: Int, depth: Option[Type], ch: Option[Int], init: Scalar)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,init,rows)

  def apply[M, T <: AnyVal](rows: Int, cols: Int)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,rows,Seq(cols):_*)
  def apply[M, T <: AnyVal](rows: Int, cols: Int, init: Scalar)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,init,rows,Seq(cols):_*)
  def apply[M, T <: AnyVal](rows: Int, cols: Int, depth: Option[Type], ch: Option[Int])(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,rows,Seq(cols):_*)
  def apply[M, T <: AnyVal](rows: Int, cols: Int, depth: Option[Type], ch: Option[Int], init: Scalar)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,init,rows,Seq(cols):_*)

  def apply[M, T <: AnyVal](dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,dim1,dims:_*)
  def apply[M, T <: AnyVal](init: Scalar,dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](None,init,dim1,dims:_*)
  def apply[M, T <: AnyVal](depth: Option[Type], ch: Option[Int], dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,dim1,dims:_*)
  def apply[M, T <: AnyVal](depth: Option[Type], ch: Option[Int], init: Scalar, dim1: Int, dims: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T], nl: NumberLike[T], tag: ClassTag[T], mag: ClassTag[M]): MappedMat[M,T] = new MappedMat[M,T](depth,ch,init,dim1,dims:_*)
 }

class MappedMat[M : ClassTag, T <: AnyVal : ClassTag : NumberLike](depth: Option[Type], channels: Int, dim1: Int, dims: Int*)
                                                                  (implicit mm: MappedMatable[M, T], m: Matable[T])
  extends BaseMat[T](depth,channels,dim1,dims:_*)
{
  def this(depth: Option[Type], ch: Option[Int], r: Int)(implicit mm: MappedMatable[M, T], m: Matable[T]) = this(depth,ch.getOrElse(mm.channels),r,Nil :_*)
  def this(depth: Option[Type], ch: Option[Int], init: Scalar, r: Int)(implicit mm: MappedMatable[M, T], m: Matable[T]) = {
    this(depth,ch.getOrElse(mm.channels),r,Nil:_*)
    put(init)
  }

  def this(depth: Option[Type], ch: Option[Int], d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T]) = this(depth,ch.getOrElse(mm.channels),d1,ds:_*)
  def this(depth: Option[Type], ch: Option[Int], init: Scalar, d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T]) = {
    this(depth,ch.getOrElse(mm.channels),d1,ds:_*)
    put(init)
  }

  def this(depth: Option[Type], r: Int)(implicit mm: MappedMatable[M, T], m: Matable[T]) = this(depth,mm.channels,r,Nil:_*)
  def this(depth: Option[Type], init: Scalar, r: Int)(implicit mm: MappedMatable[M, T], m: Matable[T]) = {
    this(depth,mm.channels,r,Nil:_*)
    put(init)
  }

  def this(depth: Option[Type], d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T]) = this(depth,mm.channels,d1,ds:_*)
  def this(depth: Option[Type], init: Scalar, d1: Int, ds: Int*)(implicit mm: MappedMatable[M, T], m: Matable[T]) = {
    this(depth,mm.channels,d1,ds:_*)
    put(init)
  }

  def get(i: Int): M = mm.getMapped(this,i)
  def get(i: Int, is: Int*): M = mm.getMapped(this, i +: is:_*)

  def getN(n: Int, i: Int): IndexedSeq[M] = mm.getMappedN(this,Array(i),n)
  def getN(n: Int, i: Int, is: Int*): IndexedSeq[M] = mm.getMappedN(this,i +: is,n)

  def put(i: Int, value: M): Unit = mm.putMapped(this,Array(i),value)
  def put(i: Int, j: Int, value: M): Unit = mm.putMapped(this,Array(i,j),value)
  def put(i: Int, j: Int, k: Int, value: M): Unit = mm.putMapped(this,Array(i,j,k),value)
  def put(indices: IndexedSeq[Int], value: M): Unit = mm.putMapped(this,indices,value)

  def put(values: Seq[M]): Unit = mm.putMappedN(this,Array(0),values)
  def put(i: Int, values: Seq[M]): Unit = mm.putMappedN(this,Array(i),values)
  def put(i: Int, j: Int, values: Seq[M]): Unit = mm.putMappedN(this,Array(i,j),values)
  def put(indices: IndexedSeq[Int], values: Seq[M]): Unit = mm.putMappedN(this,indices,values)

  def putT1(values: (Int, M)*): Unit = values.foreach { case (i, v) => put(i, v) }
  def putT2(values: (Int, Int, M)*): Unit = values.foreach { case (i, j, v) => put(i, j, v) }
  def putT3(values: (Int, Int, Int, M)*): Unit = values.foreach { case (i, j, k, v) => put(i, j, k, v) }

  def getRaw(i: Int): T = m.get(this, i)
  def getRaw(i: Int, is: Int*): T = m.get(this, i +: is: _*)

  def getRawN(n: Int, i: Int): IndexedSeq[T] = m.getN(this, Array(i), n)
  def getRawN(n: Int, i: Int, is: Int*): IndexedSeq[T] = m.getN(this, i +: is, n)

  def putRaw(i: Int, value: T): Unit = m.put(this, Array(i), value)
  def putRaw(i: Int, j: Int, value: T): Unit = m.put(this, Array(i, j), value)
  def putRaw(i: Int, j: Int, k: Int, value: T): Unit = m.put(this, Array(i, j, k), value)
  def putRaw(indices: IndexedSeq[Int], value: T): Unit = m.put(this, indices, value)

  def putRaw(values: Seq[T]): Unit = m.putN(this, Array(0), values)
  def putRaw(i: Int, values: Seq[T]): Unit = m.putN(this, Array(i), values)
  def putRaw(i: Int, j: Int, values: Seq[T]): Unit = m.putN(this, Array(i, j), values)
  def putRaw(indices: IndexedSeq[Int], values: Seq[T]): Unit = m.putN(this, indices, values)


}
