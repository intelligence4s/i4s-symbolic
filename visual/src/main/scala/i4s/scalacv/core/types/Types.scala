package i4s.scalacv.core.types

object Types extends Enumeration {
  protected case class FlagVal(flag: Int) extends super.Val {
    override def toString(): String = flag match {
      case Cv8U.flag => "Cv8U"
      case Cv8S.flag => "Cv8S"
      case Cv16U.flag => "Cv16U"
      case Cv16S.flag => "Cv16S"
      case Cv32S.flag => "Cv32S"
      case Cv32F.flag => "Cv32F"
      case Cv64F.flag => "Cv64F"
      case Cv16F.flag => "Cv16F"
    }
  }
  type Type = Value

  import scala.language.implicitConversions
  implicit def valueToTypeDepth(v: Value): FlagVal = v.asInstanceOf[FlagVal]

  val CvUndefined: FlagVal = FlagVal(-1)
  val Cv8U: FlagVal = FlagVal(0)
  val Cv8S: FlagVal = FlagVal(1)
  val Cv16U:FlagVal = FlagVal(2)
  val Cv16S:FlagVal = FlagVal(3)
  val Cv32S:FlagVal = FlagVal(4)
  val Cv32F:FlagVal = FlagVal(5)
  val Cv64F:FlagVal = FlagVal(6)
  val Cv16F:FlagVal = FlagVal(7)

}
