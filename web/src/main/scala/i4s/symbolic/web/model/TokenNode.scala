package i4s.symbolic.web.model

case class TokenNode(token: String, lemma: Option[String], posTag: Option[String], position: Int, edges: List[TokenEdge])
