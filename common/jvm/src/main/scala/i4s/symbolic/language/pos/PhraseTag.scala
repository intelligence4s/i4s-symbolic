package i4s.symbolic.language.pos

object PhraseTag extends Enumeration {
  protected case class Val(name: String) extends super.Val with GrammarTag
  type PhraseTag = Value

  implicit def valueToPhraseTag(v: Value): Val = v.asInstanceOf[Val]

  val AdjectivePhrase: Val = Val("ADJP")
  val AdverbPhrase: Val = Val("ADVP")
  val ConjunctionPhrase: Val = Val("CONJP")
  val Fragment: Val = Val("FRAG")
  val Interjection: Val = Val("INTJ")
  val ListMarker: Val = Val("LST")
  val NonConstituent: Val = Val("NAC")
  val NounPhrase: Val = Val("NP")
  val ComplexNounPhrase: Val = Val("NX")
  val PrepositionalPhrase: Val = Val("PP")
  val Parenthetical: Val = Val("PRN")
  val Particle: Val = Val("PRT")
  val QuantifierPhrase: Val = Val("QP")
  val ReducedRelativeClause: Val = Val("RRC")
  val UnlikeCoordinatedPhrase: Val = Val("UCP")
  val VerbPhrase: Val = Val("VP")
  val WhAdjectivePhrase: Val = Val("WHADJP")
  val WhAdverbPhrase: Val = Val("WHADVP")
  val WhNounPhrase: Val = Val("WHNP")
  val WhPrepositionalPhrase: Val = Val("WHPP")
  val Unknown: Val = Val("X")
}
