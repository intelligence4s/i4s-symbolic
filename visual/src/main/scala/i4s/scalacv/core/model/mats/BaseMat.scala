package i4s.scalacv.core.model.mats

import i4s.scalacv.core.types.MatTypes.MatType
import i4s.scalacv.core.types.Types.Type
import i4s.scalacv.core.types.{MatTypes, Types}
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.opencv.opencv_core

import java.nio.IntBuffer
import scala.reflect.ClassTag

class BaseMat[T <: AnyVal : ClassTag](depth: Option[Type], channels: Int, dim1: Int, dims: Int*)(implicit matable: Matable[T])
  extends org.bytedeco.opencv.opencv_core.Mat((dim1 +: dims).toArray,MatTypes.makeType(depth.getOrElse(matable.depth),channels))
{
  implicit val indexable: Indexable[T] = matable.indexer(this)

  def matType: MatType = MatTypes(MatTypes.makeType(Types(depth()),channels()))

  def shape(): Seq[Int] = (0 until dims()).map(this.size)

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
