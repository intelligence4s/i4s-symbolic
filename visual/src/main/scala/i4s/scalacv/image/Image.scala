package i4s.scalacv.image

import i4s.scalacv.core.constants.AccessFlags.AccessFlag
import i4s.scalacv.core.constants.{AccessFlags, UsageFlags}
import i4s.scalacv.core.constants.UsageFlags.UsageFlag
import i4s.scalacv.core.types.{MatTypes, Types}
import i4s.scalacv.core.types.MatTypes.MatType
import i4s.scalacv.core.types.Types.Type
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacv.{Java2DFrameConverter, OpenCVFrameConverter}
import org.bytedeco.opencv.opencv_core.{Mat, Scalar, Size, UMat}

import java.awt.image.BufferedImage
import scala.util.Using

object Image {
  def apply(): Image = new Image()

  def apply(mat: Mat): Image = {
    val image = Image()
    mat.copyTo(image)
    image
  }

  def apply(umat: UMat): Image = {
    val image = Image()
    umat.copyTo(image)
    image
  }

  import scala.language.implicitConversions
  implicit def mat2Image(mat: Mat): Image = Image(mat)
  implicit def umat2Image(mat: UMat): Image = Image(mat)
}

class Image() extends UMat() {

  def this(rows: Int, cols: Int, mtype: MatType, usageFlags: Set[UsageFlag]) = {
    this()
    create(rows,cols,mtype.id,usageFlags.foldLeft(0)(_ | _.id))
  }

  def this(rows: Int, cols: Int, mtype: MatType) = {
    this()
    create(rows,cols,mtype.id,UsageFlags.Default.id)
  }

  def this(size: Size, mtype: MatType, usageFlags: Set[UsageFlag]) = {
    this()
    create(size,mtype.id,usageFlags.foldLeft(0)(_ | _.id))
  }

  def this(size: Size, mtype: MatType) = {
    this()
    create(size,mtype.id,UsageFlags.Default.id)
  }

  def this(rows: Int, cols: Int, mtype: MatType, s: Scalar, usageFlags: Set[UsageFlag]) = {
    this(rows,cols,mtype,usageFlags)
    put(s)
  }

  def this(rows: Int, cols: Int, mtype: MatType, s: Scalar) = {
    this()
    create(rows,cols,mtype.id)
    put(s)
  }

  def this(umat: UMat) = {
    this()
    put(umat)
  }

  def this(mat: Mat) = {
    this()
    put(mat)
  }

  def getMat(flag: AccessFlag): Mat = super.getMat(flag.id)

  def toBufferedImage: BufferedImage = {
    Using.resource(new OpenCVFrameConverter.ToMat()) { openCVConverter =>
      Using.resource(openCVConverter.convert(getMat(AccessFlags.Read))) { frame =>
        Using.resource(new Java2DFrameConverter()) { java2DConverter =>
          java2DConverter.convert(frame)
        }
      }
    }
  }

  def matType: MatType = MatTypes(super.`type`)
  def dataType: Type = Types(super.depth)

  def description: String = {
    val sizeString = s"${size().width} x ${size().height()}"
    s"Image(UMat) (${dims()}) = [${sizeString}] - type: ${matType} (${`type`()}), channels = $channels, dataType = $dataType, total = $total "
  }

}
