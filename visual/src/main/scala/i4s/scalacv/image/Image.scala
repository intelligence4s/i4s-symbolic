package i4s.scalacv.image

import i4s.scalacv.core.constants.AccessFlags.AccessFlag
import i4s.scalacv.core.constants.UsageFlags.UsageFlag
import i4s.scalacv.core.types.MatTypes.MatType
import i4s.scalacv.core.types.Types.Type
import i4s.scalacv.core.types.{MatTypes, Types}
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

class Image(rows: Int, cols: Int, mtype: MatType) extends Mat(rows, cols, mtype.id) {
  def this() = this(0,0,MatTypes.Cv8UC3)

  def this(rows: Int, cols: Int, mtype: MatType, init: Scalar) = {
    this(rows,cols,mtype)
    put(init)
  }

  def this(size: Size, mtype: MatType, init: Scalar) = {
    this(size.width,size.height,mtype)
    put(init)
  }

  def this(umat: UMat) = {
    this(umat.rows,umat.cols,MatTypes(umat.`type`))
    put(umat)
  }

  def this(mat: Mat) = {
    this(mat.rows,mat.cols,MatTypes(mat.`type`))
    put(mat)
  }

  def getUMat(flag: AccessFlag): UMat = super.getUMat(flag.id)

  def toBufferedImage: BufferedImage = {
    Using.resource(new OpenCVFrameConverter.ToMat()) { openCVConverter =>
      Using.resource(openCVConverter.convert(this)) { frame =>
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
    s"Image(Mat) (${dims()}) = [${sizeString}] - type: ${matType} (${`type`()}), channels = $channels, dataType = $dataType, total = $total "
  }

}
