package i4s.scalacv.image.constants

object ThresholdMethods extends Enumeration {
  type ThresholdMethod = Value

  val Mean: Value = Value(0)
  val Gaussian: Value = Value(1)
}
