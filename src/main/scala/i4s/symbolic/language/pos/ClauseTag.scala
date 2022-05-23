package i4s.symbolic.language.pos

import enumeratum._

sealed abstract class ClauseTag(override val entryName: String) extends EnumEntry with GrammarTag

object ClauseTag extends Enum[ClauseTag] {
  val values = findValues

  case object Simple extends ClauseTag("S")
  case object Subordinate extends ClauseTag("SBAR")
  case object SubordinateQuestion extends ClauseTag("SBARQ")
  case object Inverted extends ClauseTag("SINV")
  case object InvertedYesNo extends ClauseTag("SQ")
}

