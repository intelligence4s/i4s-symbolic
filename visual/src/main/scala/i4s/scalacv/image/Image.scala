package i4s.scalacv.image

import i4s.scalacv.core.constants.AccessFlags.AccessFlag
import i4s.scalacv.core.model.mats._
import i4s.scalacv.core.model.mats.syntax._
import i4s.scalacv.core.model.{Scalar, Size}
import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types.{Cv8U, Type}
import org.bytedeco.javacv.{Java2DFrameConverter, OpenCVFrameConverter}
import org.bytedeco.opencv.opencv_core.UMat

import java.awt.image.BufferedImage
import scala.util.Using

object Image {
  def apply(): Image = new Image()

  def apply(umat: UMat): Image = new Image(umat)
  def apply(mat: org.bytedeco.opencv.opencv_core.Mat): Image = new Image(mat)

  import scala.language.implicitConversions
  implicit def mat2Image(mat: org.bytedeco.opencv.opencv_core.Mat): Image = Image(mat)
  implicit def umat2Image(mat: UMat): Image = Image(mat)
}

class Image(rows: Int, cols: Int, channels: Int = 3) extends MappedMat[Scalar,Int](Some(Types.Cv8U), Some(channels), rows, Seq(cols):_*) {
  def this() = this(1,1)

  def this(rows: Int, cols: Int, channels: Int, init: Scalar) = {
    this(rows,cols,channels = channels)
    put(init)
  }

  def this(rows: Int, cols: Int, init: Scalar) = {
    this(rows,cols)
    put(init)
  }

  def this(size: Size, init: Scalar) = {
    this(size.width,size.height)
    put(init)
  }

  def this(umat: UMat) = {
    this(umat.rows,umat.cols,umat.channels)
    assert(Types(umat.depth) == Cv8U, "We can only initialize with Mats of unsigned byte depths")
    put(umat)
  }

  def this(mat: org.bytedeco.opencv.opencv_core.Mat) = {
    this(mat.rows,mat.cols,mat.channels)
    assert(Types(mat.depth) == Cv8U, "We can only initialize with Mats of unsigned byte depths")
    put(mat)
  }

  def this(image: Image) = {
    this(image.rows,image.cols,image.channels)
    put(image)
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

  def dataType: Type = Types(super.depth)

  def description: String = {
    val sizeString = s"${size().width} x ${size().height()}"
    s"Image(Mat) (${dims()}) = [${sizeString}] - type: ${matType} (${`type`()}), channels = $channels, dataType = $dataType, total = $total "
  }

}
