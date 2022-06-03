package i4s.symbolic.language.pos

import enumeratum._

sealed abstract class PhraseTag(override val entryName: String) extends EnumEntry with GrammarTag

object PhraseTag extends Enum[PhraseTag] {
  val values = findValues

  case object AdjectivePhrase extends PhraseTag("ADJP")
  case object AdverbPhrase extends PhraseTag("ADVP")
  case object ConjunctionPhrase extends PhraseTag("CONJP")
  case object Fragment extends PhraseTag("FRAG")
  case object Interjection extends PhraseTag("INTJ")
  case object ListMarker extends PhraseTag("LST")
  case object NonConstituent extends PhraseTag("NAC")
  case object NounPhrase extends PhraseTag("NP")
  case object ComplexNounPhrase extends PhraseTag("NX")
  case object PrepositionalPhrase extends PhraseTag("PP")
  case object Parenthetical extends PhraseTag("PRN")
  case object Particle extends PhraseTag("PRT")
  case object QuantifierPhrase extends PhraseTag("QP")
  case object ReducedRelativeClause extends PhraseTag("RRC")
  case object UnlikeCoordinatedPhrase extends PhraseTag("UCP")
  case object VerbPhrase extends PhraseTag("VP")
  case object WhAdjectivePhrase extends PhraseTag("WHADJP")
  case object WhAdverbPhrase extends PhraseTag("WHADVP")
  case object WhNounPhrase extends PhraseTag("WHNP")
  case object WhPrepositionalPhrase extends PhraseTag("WHPP")
  case object Unknown extends PhraseTag("X")
}
