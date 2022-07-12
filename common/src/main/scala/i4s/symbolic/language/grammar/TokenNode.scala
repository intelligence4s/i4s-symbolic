package i4s.symbolic.language.grammar

case class TokenNode(token: String, lemma: Option[String], posTag: Option[String], position: Int, edges: List[TokenEdge])
