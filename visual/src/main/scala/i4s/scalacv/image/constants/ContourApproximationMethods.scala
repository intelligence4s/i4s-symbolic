package i4s.scalacv.image.constants

object ContourApproximationMethods extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type ContourApproximationMethod = Value

  import scala.language.implicitConversions

  implicit def valueToContourApproximationMethod(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** stores absolutely all the contour points. That is, any 2 subsequent points (x1,y1) and
   * (x2,y2) of the contour will be either horizontal, vertical or diagonal neighbors, that is,
   * max(abs(x1-x2),abs(y2-y1))==1. */
  val None = FlagVal(1)

  /** compresses horizontal, vertical, and diagonal segments and leaves only their end points.
   * For example, an up-right rectangular contour is encoded with 4 points. */
  val Simple = FlagVal(2)

  /** applies one of the flavors of the Teh-Chin chain approximation algorithm \cite TehChin89 */
  val TehChin89LI = FlagVal(3)

  /** applies one of the flavors of the Teh-Chin chain approximation algorithm \cite TehChin89 */
  val TehChin89KCOS = FlagVal(4)
}
