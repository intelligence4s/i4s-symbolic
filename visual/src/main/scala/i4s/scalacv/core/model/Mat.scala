package i4s.scalacv.core.model

import i4s.scalacv.core.types.MatTypes
import i4s.scalacv.core.types.MatTypes.MatType
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.javacpp.indexer.Indexer
import org.bytedeco.opencv.opencv_core

import java.nio.IntBuffer

class Mat[T](size: Size, channels: Int)(implicit matable: Matable[T])
  extends org.bytedeco.opencv.opencv_core.Mat(size,MatTypes.makeType(matable.depth,channels))
{
  def this(c: Int)(implicit matable: Matable[T]) = this(Size(1,c),matable.channels)
  def this(r: Int, c: Int)(implicit matable: Matable[T]) = this(Size(r,c),matable.channels)
  def this(c: Int, ch: Option[Int])(implicit matable: Matable[T]) = this(Size(1,c),ch.getOrElse(matable.channels))
  def this(r: Int, c: Int, ch: Option[Int])(implicit matable: Matable[T]) = this(Size(r,c),ch.getOrElse(matable.channels))

  implicit val indexer: Indexer = matable.indexer(this)

  def matType: MatType = MatTypes(MatTypes.makeType(matable.depth,channels))

  def get(i: Int): T = matable.get(this,i)
  def get(i: Int, j: Int): T = matable.get(this,i,j)
  def get(i: Int, j: Int, k: Int): T = matable.get(this,i,j,k)
  def get(indices: Int*): T = matable.get(this,indices:_*)

  def getN(i: Int, n: Int): IndexedSeq[T] = matable.getN(this,Array(i),n)
  def getN(i: Int, j: Int, n: Int): IndexedSeq[T] = matable.getN(this,Array(i,j),n)
  def getN(indices: IndexedSeq[Int], n: Int): IndexedSeq[T] = matable.getN(this,indices,n)

  def getAll(i: Int): IndexedSeq[T] = getN(i,total(i).toInt)
  def getAll(i: Int, j: Int): Seq[T] = getN(i,j,total(i,j).toInt)

  def put(i: Int, value: T): Unit = matable.put(this,Array(i),value)
  def put(i: Int, j: Int, value: T): Unit = matable.put(this,Array(i,j),value)
  def put(i: Int, j: Int, k: Int, value: T): Unit = matable.put(this,Array(i,j,k),value)

  def put(indices: IndexedSeq[Int], value: T): Unit = matable.put(this,indices,value)

  def putT1(values: (Int, T)*): Unit = values.foreach { case (i, v) => put(i, v) }
  def putT2(values: (Int, Int, T)*): Unit = values.foreach { case (i, j, v) => put(i, j, v) }
  def putT3(values: (Int, Int, Int, T)*): Unit = values.foreach { case (i, j, k, v) => put(i, j, k, v) }

  def putAll(i: Int, values: Seq[T]): Unit = matable.putN(this,Array(i),values)
  def putAll(i: Int, j: Int, values: Seq[T]): Unit = matable.putN(this,Array(i,j),values)

  // Disallow calls to underlaying Mat object that will cause side-effects...
  override def create(size: opencv_core.Size, `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

  override def create(ndims: Int, sizes: Array[Int], `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

  override def create(ndims: Int, sizes: IntPointer, `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

  override def create(rows: Int, cols: Int, `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

  override def create(sizes: Array[Int], `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

  override def create(sizes: IntBuffer, `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

  override def create(sizes: IntPointer, `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

  override def create(ndims: Int, sizes: IntBuffer, `type`: Int): Unit =
    throw new UnsupportedOperationException(s"Side effects changing the size, shape or allocation of the underlaying Mat object are not allowed from the Scala wrapper")

}
