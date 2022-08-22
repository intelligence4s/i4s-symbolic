package i4s.scalacv.image.constants

object MorphShapes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type MorphShape = Value

  import scala.language.implicitConversions

  implicit def valueToMorphShape(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val Rectangle: FlagVal = FlagVal(0)

  /** a cross-shaped structuring element:
   * <pre> \[E_{ij} = \begin{cases} 1 & \texttt{if } {i=\texttt{anchor.y } {or } {j=\texttt{anchor.x}}} \\0 & \texttt{otherwise} \end{cases}\]}</pre> */
  val Cross: FlagVal = FlagVal(1)

  /** an elliptic structuring element, that is, a filled ellipse inscribed
   * into the rectangle Rect(0, 0, esize.width, 0.esize.height) */
  val Ellipse: FlagVal = FlagVal(2)

}
