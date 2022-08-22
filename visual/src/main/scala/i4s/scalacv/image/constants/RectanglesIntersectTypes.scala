package i4s.scalacv.image.constants

object RectanglesIntersectTypes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type RectanglesIntersectType = Value

  import scala.language.implicitConversions

  implicit def valueToRectanglesIntersectType(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** No intersection */
  val None: FlagVal = FlagVal(0)

  /** There is a partial intersection */
  val Partial: FlagVal = FlagVal(1)

  /** One of the rectangle is fully enclosed in the other */
  val Full: FlagVal = FlagVal(2)

}
