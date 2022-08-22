package i4s.scalacv.image.constants

object RetrievalModes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type RetrievalMode = Value

  import scala.language.implicitConversions

  implicit def valueToRetrievalMode(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** retrieves only the extreme outer contours. It sets {@code hierarchy[i][2]=hierarchy[i][3]=-1} for
   * all the contours. */
  val External = FlagVal(0)

  /** retrieves all of the contours without establishing any hierarchical relationships. */
  val List = FlagVal(1)

  /** retrieves all of the contours and organizes them into a two-level hierarchy. At the top
   * level, there are external boundaries of the components. At the second level, there are
   * boundaries of the holes. If there is another contour inside a hole of a connected component, it
   * is still put at the top level. */
  val ConnectedComponent = FlagVal(2)

  /** retrieves all of the contours and reconstructs a full hierarchy of nested contours. */
  val Tree = FlagVal(3)

  //!<
  val FloodFill = FlagVal(4)

}
