package i4s.symbolic.language.ud

import enumeratum._

sealed abstract class SyntacticRelation(override val entryName:String) extends EnumEntry

object SyntacticRelation extends Enum[SyntacticRelation] {
  val values = findValues

  case object AdnominalClause extends SyntacticRelation("acl")
  case object AdverbialClause extends SyntacticRelation("advcl")
  case object AdverbModifier extends SyntacticRelation("advmod")
  case object AdjectiveModifier extends SyntacticRelation("amod")
  case object AppositionalModifier extends SyntacticRelation("appos")
  case object Auxiliary extends SyntacticRelation("aux")
  case object CaseMarker extends SyntacticRelation("case")
  case object CoordinatingConjunction extends SyntacticRelation("cc")
  case object ClausalComplement extends SyntacticRelation("ccomp")
  case object Classifier extends SyntacticRelation("clf")
  case object Compound extends SyntacticRelation("compound")
  case object Conjunct extends SyntacticRelation("conj")
  case object ConjunctAnd extends SyntacticRelation("conj_and")
  case object ConjunctOr extends SyntacticRelation("conj_or")
  case object Copula extends SyntacticRelation("cop")
  case object ClausalSubject extends SyntacticRelation("csubj")
  case object Dependency extends SyntacticRelation("dep")
  case object Determiner extends SyntacticRelation("det")
  case object Discourse extends SyntacticRelation("discourse")
  case object Dislocated extends SyntacticRelation("dislocated")
  case object Expletive extends SyntacticRelation("expl")
  case object Fixed extends SyntacticRelation("fixed")
  case object Flat extends SyntacticRelation("flat")
  case object GoesWith extends SyntacticRelation("goeswith")
  case object IndirectObject extends SyntacticRelation("iobj")
  case object List extends SyntacticRelation("list")
  case object Marker extends SyntacticRelation("mark")
  case object NominalModifier extends SyntacticRelation("nmod")
  case object NumericModifier extends SyntacticRelation("nummod")
  case object NominalSubject extends SyntacticRelation("nsubj")
  case object Object extends SyntacticRelation("obj")
  case object Oblique extends SyntacticRelation("obl")
  case object Orphan extends SyntacticRelation("orphan")
  case object Parataxis extends SyntacticRelation("parataxis")
  case object Punctuation extends SyntacticRelation("punct")
  case object Reparandum extends SyntacticRelation("reparandum")
  case object Root extends SyntacticRelation("root")
  case object Vocative extends SyntacticRelation("vocative")
  case object ObligatoryClausalComplement extends SyntacticRelation("xcomp")

  // Modifiers
  case object EmphasisWord extends SyntacticRelation("emph")
  case object LocativeModifier extends SyntacticRelation("lmod")
  case object LightVerbConstruction extends SyntacticRelation("lvc")
  case object PhrasalVerbParticle extends SyntacticRelation("prt")
  case object ReduplicatedCompounds extends SyntacticRelation("redup")
  case object SerialVerbCompounds extends SyntacticRelation("svc")
  case object PassiveAuxiliary extends SyntacticRelation("pass")
  case object Preconjunct extends SyntacticRelation("preconj")
  case object ObligatorySubject extends SyntacticRelation("xsubj")
  case object RelativeClause extends SyntacticRelation("relcl")
}
