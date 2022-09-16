package i4s.scalacv.core.model.mats

import i4s.scalacv.core.model.Scalar
import i4s.scalacv.core.types.{MatTypes, Types}
import i4s.scalacv.core.types.MatTypes.MatType
import i4s.scalacv.core.types.Types.Type
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.opencv.opencv_core

import java.nio.IntBuffer
import scala.reflect.ClassTag

object Mat {
  def apply[T <: AnyVal](rows: Int)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](None,rows)
  def apply[T <: AnyVal](rows: Int, init: Scalar)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](None,init,rows)
  def apply[T <: AnyVal](rows: Int, depth: Option[Type], ch: Option[Int])(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](depth,ch,rows)
  def apply[T <: AnyVal](rows: Int, depth: Option[Type], ch: Option[Int], init: Scalar)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](depth,ch,init,rows)

  def apply[T <: AnyVal](rows: Int, cols: Int)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](None,rows,Seq(cols):_*)
  def apply[T <: AnyVal](rows: Int, cols: Int, init: Scalar)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](None,init,rows,Seq(cols):_*)
  def apply[T <: AnyVal](rows: Int, cols: Int, depth: Option[Type], ch: Option[Int])(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](depth,ch,rows,Seq(cols):_*)
  def apply[T <: AnyVal](rows: Int, cols: Int, depth: Option[Type], ch: Option[Int], init: Scalar)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](depth,ch,init,rows,Seq(cols):_*)

  def apply[T <: AnyVal](dim1: Int, dims: Int*)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](None,dim1,dims:_*)
  def apply[T <: AnyVal](init: Scalar,dim1: Int, dims: Int*)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](None,init,dim1,dims:_*)
  def apply[T <: AnyVal](depth: Option[Type], ch: Option[Int], dim1: Int, dims: Int*)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](depth,ch,dim1,dims:_*)
  def apply[T <: AnyVal](depth: Option[Type], ch: Option[Int], init: Scalar, dim1: Int, dims: Int*)(implicit matable: Matable[T], tag: ClassTag[T]): Mat[T] = new Mat[T](depth,ch,init,dim1,dims:_*)
}

class Mat[T <: AnyVal : ClassTag](depth: Option[Type], channels: Int, dim1: Int, dims: Int*)(implicit matable: Matable[T])
  extends BaseMat[T](depth,channels,dim1,dims:_*)
{
  def this(depth: Option[Type], ch: Option[Int], r: Int)(implicit matable: Matable[T]) = this(depth,ch.getOrElse(matable.channels),r,Nil :_*)
  def this(depth: Option[Type], ch: Option[Int], init: Scalar, r: Int)(implicit matable: Matable[T]) = {
    this(depth, ch.getOrElse(matable.channels),r,Nil:_*)
    put(init)
  }

  def this(depth: Option[Type], ch: Option[Int], d1: Int, ds: Int*)(implicit matable: Matable[T]) = this(depth,ch.getOrElse(matable.channels),d1,ds:_*)
  def this(depth: Option[Type], ch: Option[Int], init: Scalar, d1: Int, ds: Int*)(implicit matable: Matable[T]) = {
    this(depth,ch.getOrElse(matable.channels),d1,ds:_*)
    put(init)
  }

  def this(depth: Option[Type], r: Int)(implicit matable: Matable[T]) = this(depth, matable.channels,r,Nil:_*)
  def this(depth: Option[Type], init: Scalar, r: Int)(implicit matable: Matable[T]) = {
    this(depth, matable.channels,r,Nil:_*)
    put(init)
  }

  def this(depth: Option[Type], d1: Int, ds: Int*)(implicit matable: Matable[T]) = this(depth,matable.channels,d1,ds:_*)
  def this(depth: Option[Type], init: Scalar, d1: Int, ds: Int*)(implicit matable: Matable[T]) = {
    this(depth,matable.channels,d1,ds:_*)
    put(init)
  }

  def get(i: Int): T = matable.get(this,i)
  def get(i: Int, is: Int*): T = matable.get(this, i +: is:_*)

  def getN(n: Int, i: Int): IndexedSeq[T] = matable.getN(this,Array(i),n)
  def getN(n: Int, i: Int, is: Int*): IndexedSeq[T] = matable.getN(this,i +: is,n)

  def put(i: Int, value: T): Unit = matable.put(this,Array(i),value)
  def put(i: Int, j: Int, value: T): Unit = matable.put(this,Array(i,j),value)
  def put(i: Int, j: Int, k: Int, value: T): Unit = matable.put(this,Array(i,j,k),value)
  def put(indices: IndexedSeq[Int], value: T): Unit = matable.put(this,indices,value)

  def putT1(values: (Int, T)*): Unit = values.foreach { case (i, v) => put(i, v) }
  def putT2(values: (Int, Int, T)*): Unit = values.foreach { case (i, j, v) => put(i, j, v) }
  def putT3(values: (Int, Int, Int, T)*): Unit = values.foreach { case (i, j, k, v) => put(i, j, k, v) }

  def putAll(values: Seq[T]): Unit = matable.putN(this,Array(0),values)
  def putAll(i: Int, values: Seq[T]): Unit = matable.putN(this,Array(i),values)
  def putAll(i: Int, j: Int, values: Seq[T]): Unit = matable.putN(this,Array(i,j),values)

  def shape(): Seq[Int] = (0 until dims()).map(this.size)
}
