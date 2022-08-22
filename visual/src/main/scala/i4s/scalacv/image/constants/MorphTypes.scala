package i4s.scalacv.image.constants

object MorphTypes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type MorphType = Value

  import scala.language.implicitConversions

  implicit def valueToMorphType(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val Erode: FlagVal = FlagVal(0)

  /** see #dilate */
  val Dilate: FlagVal = FlagVal(1)

  /** an opening operation
   * <pre> \[\texttt{dst} = \mathrm{open} ( \texttt{src} , \texttt{element} )= \mathrm{dilate} ( \mathrm{erode} ( \texttt{src} , \texttt{element} ))\]}</pre> */
  val Open: FlagVal = FlagVal(2)

  /** a closing operation
   * <pre> \[\texttt{dst} = \mathrm{close} ( \texttt{src} , \texttt{element} )= \mathrm{erode} ( \mathrm{dilate} ( \texttt{src} , \texttt{element} ))\]}</pre> */
  val Close: FlagVal = FlagVal(3)

  /** a morphological gradient
   * <pre> \[\texttt{dst} = \mathrm{morph\_grad} ( \texttt{src} , \texttt{element} )= \mathrm{dilate} ( \texttt{src} , \texttt{element} )- \mathrm{erode} ( \texttt{src} , \texttt{element} )\]}</pre> */
  val Gradient: FlagVal = FlagVal(4)

  /** "top hat"
   * <pre> \[\texttt{dst} = \mathrm{tophat} ( \texttt{src} , \texttt{element} )= \texttt{src} - \mathrm{open} ( \texttt{src} , \texttt{element} )\]}</pre> */
  val TopHat: FlagVal = FlagVal(5)

  /** "black hat"
   * <pre> \[\texttt{dst} = \mathrm{blackhat} ( \texttt{src} , \texttt{element} )= \mathrm{close} ( \texttt{src} , \texttt{element} )- \texttt{src}\]}</pre> */
  val BlackHat: FlagVal = FlagVal(6)

  /** "hit or miss"
   * .- Only supported for CV_8UC1 binary images. A tutorial can be found in the documentation */
  val HitOrMiss: FlagVal = FlagVal(7)

}
