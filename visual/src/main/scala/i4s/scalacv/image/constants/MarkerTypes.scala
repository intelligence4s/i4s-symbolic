package i4s.scalacv.image.constants

object MarkerTypes extends Enumeration {
  protected case class TypeVal(flag: Int) extends super.Val

  type MarkerType = Value

  import scala.language.implicitConversions

  implicit def valueToMarkerType(v: Value): TypeVal = v.asInstanceOf[TypeVal]

  /** A crosshair marker shape */
  val Cross: TypeVal = TypeVal(0)

  /** A 45 degree tilted crosshair marker shape */
  val TiltedCross: TypeVal = TypeVal(1)

  /** A star marker shape, combination of cross and tilted cross */
  val Star: TypeVal = TypeVal(2)

  /** A diamond marker shape */
  val Diamond: TypeVal = TypeVal(3)

  /** A square marker shape */
  val Square: TypeVal = TypeVal(4)

  /** An upwards pointing triangle marker shape */
  val TriangleUp: TypeVal = TypeVal(5)

  /** A downwards pointing triangle marker shape */
  val TriangleDown: TypeVal = TypeVal(6)
}
