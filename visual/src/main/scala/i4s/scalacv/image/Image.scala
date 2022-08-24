package i4s.scalacv.image

import i4s.scalacv.core.constants.UsageFlags
import i4s.scalacv.core.constants.UsageFlags.UsageFlag
import i4s.scalacv.core.types.MatTypes
import i4s.scalacv.core.types.MatTypes.MatType
import org.bytedeco.javacpp.Pointer
import org.bytedeco.opencv.opencv_core.{Mat, Scalar, Size, UMat}

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

  implicit class imageTools(image: Image) {
    def description: String = {
      val sizeString = s"${image.size().width} x ${image.size().height()}"
      val matType = MatTypes.toEnum(image.`type`())
      s"Mat (${image.dims()}) = [${sizeString}] - type: ${image.`type`()} == ${matType}"
    }
  }
}

/*
class Image(p: Pointer) extends UMat(p) {

  def this() = this(null.asInstanceOf[Pointer])

  def this(rows: Int, cols: Int, mtype: MatType, usageFlags: Set[UsageFlag]) = {
    this()
    create(rows,cols,mtype.flag,usageFlags.foldLeft(0)(_ | _.flag))
  }

  def this(rows: Int, cols: Int, mtype: MatType) = {
    this()
    create(rows,cols,mtype.flag,UsageFlags.Default.flag)
  }

  def this(size: Size, mtype: MatType, usageFlags: Set[UsageFlag]) = {
    this()
    create(size,mtype.flag,usageFlags.foldLeft(0)(_ | _.flag))
  }

  def this(size: Size, mtype: MatType) = {
    this()
    create(size,mtype.flag,UsageFlags.Default.flag)
  }

  def this(rows: Int, cols: Int, mtype: MatType, s: Scalar, usageFlags: Set[UsageFlag]) = {
    this(rows,cols,mtype,usageFlags)
    put(s)
  }

  def this(rows: Int, cols: Int, mtype: MatType, s: Scalar) = {
    this()
    create(rows,cols,mtype.flag)
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
}
*/
