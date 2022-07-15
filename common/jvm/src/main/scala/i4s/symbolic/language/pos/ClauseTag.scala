package i4s.symbolic.language.pos

object ClauseTag extends Enumeration {
  protected case class Val(name: String) extends super.Val with GrammarTag
  type ClauseTag = Value

  implicit def valueToClauseTag(v: Value): Val = v.asInstanceOf[Val]

  val Simple: Val = Val("S")
  val Subordinate: Val = Val("SBAR")
  val SubordinateQuestion: Val = Val("SBARQ")
  val Inverted: Val = Val("SINV")
  val InvertedYesNo: Val = Val("SQ")
}



