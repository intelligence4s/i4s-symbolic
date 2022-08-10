package i4s.scalacv.image

object ImageReadFlags extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val
  type ImageReadFlags = Value

  import scala.language.implicitConversions
  implicit def valueToImageReadFlag(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** If set, return the loaded image as is (with alpha channel, otherwise it gets cropped). Ignore EXIF orientation. */
  val Unchanged: FlagVal = FlagVal(-1)

  /** If set, always convert image to the single channel grayscale image (codec internal conversion). */
  val GrayScale: FlagVal = FlagVal(0)

  /** If set, always convert image to the 3 channel BGR color image. */
  val Color: FlagVal = FlagVal(1)

  /** If set, return 16-bit/32-bit image when the input has the corresponding depth, otherwise convert it to 8-bit. */
  val AnyDepth: FlagVal = FlagVal(2)

  /** If set, the image is read in any possible color format. */
  val AnyColor: FlagVal = FlagVal(4)

  /** If set, use the gdal driver for loading the image. */
  val LoadGdal: FlagVal = FlagVal(8)

  /** If set, always convert image to the single channel grayscale image and the image size reduced 1/2. */
  val ReducedGrayScale: FlagVal = FlagVal(16)

  /** If set, always convert image to the 3 channel BGR color image and the image size reduced 1/2. */
  val ReducedColor2: FlagVal = FlagVal(17)

  /** If set, always convert image to the single channel grayscale image and the image size reduced 1/4. */
  val ReducedGrayScale4: FlagVal = FlagVal(32)

  /** If set, always convert image to the 3 channel BGR color image and the image size reduced 1/4. */
  val ReducedColor4: FlagVal = FlagVal(33)

  /** If set, always convert image to the single channel grayscale image and the image size reduced 1/8. */
  val ReducedGrayScale8: FlagVal = FlagVal(64)

  /** If set, always convert image to the 3 channel BGR color image and the image size reduced 1/8. */
  val ReducedColor8: FlagVal = FlagVal(65)

  /** If set, do not rotate the image according to EXIF's orientation flag. */
  val IgnoreOrientation: FlagVal = FlagVal(128)

}
