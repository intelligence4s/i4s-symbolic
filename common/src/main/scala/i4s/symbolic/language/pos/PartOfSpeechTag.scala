package i4s.symbolic.language.pos

import enumeratum._

sealed abstract class PartOfSpeechTag(override val entryName: String, isVerb: Boolean = false, isNoun: Boolean = false) extends EnumEntry with GrammarTag
object PartOfSpeechTag extends Enum[PartOfSpeechTag] {

  val values = findValues

  // Noun variations...
  case object Noun extends PartOfSpeechTag("NN", isNoun = true)
  case object NounPlural extends PartOfSpeechTag("NNS", isNoun = true)
  case object NounProper extends PartOfSpeechTag("NNP", isNoun = true)
  case object NounProperPlural extends PartOfSpeechTag("NNPS", isNoun = true)

  // Pronoun variations...
  case object PronounPersonal extends PartOfSpeechTag("PRP")
  case object PronounPossessive extends PartOfSpeechTag("PRP$")
  case object PronounWh extends PartOfSpeechTag("WP")
  case object PronounWhPossessive extends PartOfSpeechTag("WP$")

  // Verb variations...
  case object Verb extends PartOfSpeechTag("VB", isVerb = true)
  case object VerbPastTense extends PartOfSpeechTag("VBD", isVerb = true)
  case object VerbGerund extends PartOfSpeechTag("VBG", isVerb = true)
  case object VerbPastPart extends PartOfSpeechTag("VBN", isVerb = true)
  case object VerbThirdPerson extends PartOfSpeechTag("VBZ", isVerb = true)
  case object VerbNonThirdPerson extends PartOfSpeechTag("VBP", isVerb = true)

  // Adjective variations...
  case object Adjective extends PartOfSpeechTag("JJ")
  case object AdjectiveComparative extends PartOfSpeechTag("JJR")
  case object AdjectiveSuperlative extends PartOfSpeechTag("JJS")

  // Adverb variations...
  case object Adverb extends PartOfSpeechTag("RB")
  case object AdverbComparative extends PartOfSpeechTag("RBR")
  case object AdverbSuperlative extends PartOfSpeechTag("RBS")
  case object AdverbWh extends PartOfSpeechTag("WRB")

  // Miscellaneous POS...
  case object Conjunction extends PartOfSpeechTag("CC")
  case object CardinalNumber extends PartOfSpeechTag("CD")
  case object Determiner extends PartOfSpeechTag("DT")
  case object ExistentialThere extends PartOfSpeechTag("EX")
  case object ForeignWord extends PartOfSpeechTag("FW")

  case object Preposition extends PartOfSpeechTag("IN")
  case object WhDeterminer extends PartOfSpeechTag("WDT")
  case object Interjection extends PartOfSpeechTag("UH")

  case object ListItemMarker extends PartOfSpeechTag("LS")
  case object Modal extends PartOfSpeechTag("MD")
  case object Predeterminer extends PartOfSpeechTag("PDT")
  case object To extends PartOfSpeechTag("TO")
  case object Symbol extends PartOfSpeechTag("SYM")
  case object Particle extends PartOfSpeechTag("RP")
  case object PossessiveEnding extends PartOfSpeechTag("PRP$")

  case object Period extends PartOfSpeechTag(".")
  case object Separator extends PartOfSpeechTag(":")
  case object Comma extends PartOfSpeechTag(",")

  case object LeftParen extends PartOfSpeechTag("-LRB-")
  case object RightParen extends PartOfSpeechTag("-RRB-")
}
