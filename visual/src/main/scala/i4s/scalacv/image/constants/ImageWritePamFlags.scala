package i4s.scalacv.image.constants

object ImageWritePamFlags extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type ImageReadFlags = Value

  import scala.language.implicitConversions

  implicit def valueToImageReadFlag(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val NullFormat: FlagVal = FlagVal(0)
  val BlackAndWhite: FlagVal = FlagVal(1)
  val Grayscale: FlagVal = FlagVal(2)
  val GrayscaleAlpha: FlagVal = FlagVal(3)
  val Rgb: FlagVal = FlagVal(4)
  val RgbAlpha: FlagVal = FlagVal(5)

}
