package i4s.scalacv.image.constants

object ImageWriteExrCompressionFlags extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type ImageReadFlags = Value

  import scala.language.implicitConversions

  implicit def valueToImageReadFlag(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /*IMWRITE_EXR_TYPE_UNIT = 0, //!< not supported */
  /** store as HALF (FP16) */
  val Half: FlagVal = FlagVal(1)

  /** store as FP32 (default) */
  val Float: FlagVal = FlagVal(2)

}
