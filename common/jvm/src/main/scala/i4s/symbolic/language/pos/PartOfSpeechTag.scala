package i4s.symbolic.language.pos

object PartOfSpeechTag extends Enumeration {
  protected case class Val(name: String, isVerb: Boolean = false, isNoun: Boolean = false) extends super.Val with GrammarTag
  type PartOfSpeechTag = Value

  implicit def valueToPartOfSpeechTag(v: Value): Val = v.asInstanceOf[Val]

  // Noun variations...
  val Noun: Val = Val("NN", isNoun = true)
  val NounPlural: Val = Val("NNS", isNoun = true)
  val NounProper: Val = Val("NNP", isNoun = true)
  val NounProperPlural: Val = Val("NNPS", isNoun = true)

  // Pronoun variations...
  val PronounPersonal: Val = Val("PRP")
  val PronounPossessive: Val = Val("PRP$")
  val PronounWh: Val = Val("WP")
  val PronounWhPossessive: Val = Val("WP$")

  // Verb variations...
  val Verb: Val = Val("VB", isVerb = true)
  val VerbPastTense: Val = Val("VBD", isVerb = true)
  val VerbGerund: Val = Val("VBG", isVerb = true)
  val VerbPastPart: Val = Val("VBN", isVerb = true)
  val VerbThirdPerson: Val = Val("VBZ", isVerb = true)
  val VerbNonThirdPerson: Val = Val("VBP", isVerb = true)

  // Adjective variations...
  val Adjective: Val = Val("JJ")
  val AdjectiveComparative: Val = Val("JJR")
  val AdjectiveSuperlative: Val = Val("JJS")

  // Adverb variations...
  val Adverb: Val = Val("RB")
  val AdverbComparative: Val = Val("RBR")
  val AdverbSuperlative: Val = Val("RBS")
  val AdverbWh: Val = Val("WRB")

  // Miscellaneous POS...
  val Conjunction: Val = Val("CC")
  val CardinalNumber: Val = Val("CD")
  val Determiner: Val = Val("DT")
  val ExistentialThere: Val = Val("EX")
  val ForeignWord: Val = Val("FW")

  val Preposition: Val = Val("IN")
  val WhDeterminer: Val = Val("WDT")
  val Interjection: Val = Val("UH")

  val ListItemMarker: Val = Val("LS")
  val Modal: Val = Val("MD")
  val Predeterminer: Val = Val("PDT")
  val To: Val = Val("TO")
  val Symbol: Val = Val("SYM")
  val Particle: Val = Val("RP")
  val PossessiveEnding: Val = Val("PRP$")

  val Period: Val = Val(".")
  val Separator: Val = Val(":")
  val Comma: Val = Val(",")

  val LeftParen: Val = Val("-LRB-")
  val RightParen: Val = Val("-RRB-")
}
