package i4s.scalacv.image.constants

object ThresholdMethods extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type ThresholdMethod = Value

  import scala.language.implicitConversions

  implicit def valueToThresholdMethod(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val Mean: FlagVal = FlagVal(0)
  val Gaussian: FlagVal = FlagVal(1)
}
