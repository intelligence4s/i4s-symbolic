package i4s.scalacv.image.constants

object LineTypes extends Enumeration {
  protected case class TypeVal(flag: Int) extends super.Val

  type LineType = Value

  import scala.language.implicitConversions

  implicit def valueToImageReadFlag(v: Value): TypeVal = v.asInstanceOf[TypeVal]

  val Filled: TypeVal = TypeVal(-1)

  /** 4-connected line */
  val Line4: TypeVal = TypeVal(4)

  /** 8-connected line */
  val Line8: TypeVal = TypeVal(8)

  /** anti-=aliased line */
  val LineAntiAliased: TypeVal = TypeVal(16)
}
