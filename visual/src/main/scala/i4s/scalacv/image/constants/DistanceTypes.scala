package i4s.scalacv.image.constants

object DistanceTypes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type DistanceType = Value

  import scala.language.implicitConversions

  implicit def valueToDistanceType(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** User defined distance */
  val User: FlagVal = FlagVal(-1)

  /** distance = |x1-x2| + |y1-y2| */
  val L1: FlagVal = FlagVal(1)

  /** the simple euclidean distance */
  val L2: FlagVal = FlagVal(2)

  /** distance = max(|x1-x2|,|y1-y2|) */
  val C: FlagVal = FlagVal(3)

  /** L1-L2 metric: distance = 2(sqrt(1+x*x/2) - 1)) */
  val L12: FlagVal = FlagVal(4)

  /** distance = c^2(|x|/c-log(1+|x|/c)), c = 1.3998 */
  val Fair: FlagVal = FlagVal(5)

  /** distance = c^2/2(1-exp(-(x/c)^2)), c = 2.9846 */
  val Welsch: FlagVal = FlagVal(6)

  /** distance = |x|<c ? x^2/2 : c(|x|-c/2), c=1.345 */
  val Huber: FlagVal = FlagVal(7)

}
