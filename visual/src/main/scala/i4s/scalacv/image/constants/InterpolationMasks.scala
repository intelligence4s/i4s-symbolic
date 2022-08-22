package i4s.scalacv.image.constants

object InterpolationMasks extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type InterpolationMask = Value

  import scala.language.implicitConversions

  implicit def valueToInterpolationMask(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val Bits: FlagVal = FlagVal(5)

  val Bits2: FlagVal = FlagVal(Bits.flag * 2)

  val TabSize: FlagVal = FlagVal(1 << Bits.flag)

  val TabSize2: FlagVal = FlagVal(TabSize.flag * TabSize.flag)
}
