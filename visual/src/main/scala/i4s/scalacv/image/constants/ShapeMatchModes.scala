package i4s.scalacv.image.constants

object ShapeMatchModes extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val

  type ShapeMatchMode = Value

  import scala.language.implicitConversions

  implicit def valueToShapeMatchMode(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  /** <pre>\[I_1(A,B) =  \sum _{i=1...7}  \left |  \frac{1}{m^A_i} -  \frac{1}{m^B_i} \right |\]</pre> */
  val I1: FlagVal = FlagVal(1)

  /** <pre>\[I_2(A,B) =  \sum _{i=1...7}  \left | m^A_i - m^B_i  \right |\]</pre> */
  val I2: FlagVal = FlagVal(2)

  /** <pre>\[I_3(A,B) =  \max _{i=1...7}  \frac{ \left| m^A_i - m^B_i \right| }{ \left| m^A_i \right| }\]</pre> */
  val I3: FlagVal = FlagVal(3)

}
