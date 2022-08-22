package i4s.scalacv.image.constants

object DistanceLabelTypes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type DistanceLabelType = Value

  import scala.language.implicitConversions

  implicit def valueToDistanceLabelType(v: Value): FlagVal = v.asInstanceOf[FlagVal]


  /** each connected component of zeros in src (as well as all the non-zero pixels closest to the
   * connected component) will be assigned the same label */
  val ConnectedComponent: FlagVal = FlagVal(0)

  /** each zero pixel (and all the non-zero pixels closest to it) gets its own label. */
  val Pixel: FlagVal = FlagVal(1)
}
