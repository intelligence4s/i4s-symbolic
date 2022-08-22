package i4s.scalacv.image.constants

object SpecialFilters extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type SpecialFilter = Value

  import scala.language.implicitConversions

  implicit def valueToSpecialFilter(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val Scharr: FlagVal = FlagVal(-1)
}
