package i4s.scalacv.core.constants

object BorderTypes extends Enumeration {
  protected case class TypeVal(flag: Int) extends super.Val

  type BorderType = Value

  import scala.language.implicitConversions

  implicit def valueToBorderType(v: Value): TypeVal = v.asInstanceOf[TypeVal]

  /** <pre>iiiiii|abcdefgh|iiiiiii</pre>  with some specified <pre>i</pre> */
  val Constant: TypeVal = TypeVal(0)

  /** <pre>aaaaaa|abcdefgh|hhhhhhh</pre> */
  val Replicate: TypeVal = TypeVal(1)

  /** <pre>fedcba|abcdefgh|hgfedcb</pre> */
  val Reflect: TypeVal = TypeVal(2)

  /** <pre>cdefgh|abcdefgh|abcdefg</pre> */
  val Wrap: TypeVal = TypeVal(3)

  /** <pre>gfedcb|abcdefgh|gfedcba</pre> */
  val Reflect101: TypeVal = TypeVal(4)

  /** <pre>uvwxyz|abcdefgh|ijklmno</pre> */
  val Transparent: TypeVal = TypeVal(5)

  /** same as BORDER_REFLECT_101 */
  val Default: TypeVal = Reflect101

  /** do not look outside of ROI */
  val Isolated: TypeVal = TypeVal(16)
}
