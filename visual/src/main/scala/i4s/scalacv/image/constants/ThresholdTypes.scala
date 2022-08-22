package i4s.scalacv.image.constants

object ThresholdTypes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type ThresholdType = Value

  import scala.language.implicitConversions

  implicit def valueToThresholdType(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** <pre>\[\texttt{dst} (x,y) =  \fork{\texttt{maxval}}{if \(\texttt{src}(x,y) > \texttt{thresh}\)}{0}{otherwise}\]</pre> */
  val Binary: FlagVal = FlagVal(0)

  /** <pre>\[\texttt{dst} (x,y) =  \fork{0}{if \(\texttt{src}(x,y) > \texttt{thresh}\)}{\texttt{maxval}}{otherwise}\]</pre> */
  val BinaryInverted: FlagVal = FlagVal(1)

  /** <pre>\[\texttt{dst} (x,y) =  \fork{\texttt{threshold}}{if \(\texttt{src}(x,y) > \texttt{thresh}\)}{\texttt{src}(x,y)}{otherwise}\]</pre> */
  val Truncate: FlagVal = FlagVal(2)

  /** <pre>\[\texttt{dst} (x,y) =  \fork{\texttt{src}(x,y)}{if \(\texttt{src}(x,y) > \texttt{thresh}\)}{0}{otherwise}\]</pre> */
  val ToZero: FlagVal = FlagVal(3)

  /** <pre>\[\texttt{dst} (x,y) =  \fork{0}{if \(\texttt{src}(x,y) > \texttt{thresh}\)}{\texttt{src}(x,y)}{otherwise}\]</pre> */
  val ToZeroInverted: FlagVal = FlagVal(4)

  val Mask: FlagVal = FlagVal(7)

  /** flag, use Otsu algorithm to choose the optimal threshold value */
  val Otsu: FlagVal = FlagVal(8)

  /** flag, use Triangle algorithm to choose the optimal threshold value */
  val Triangle: FlagVal = FlagVal(16)

}
