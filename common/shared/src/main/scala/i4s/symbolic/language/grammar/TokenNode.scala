package i4s.symbolic.language.grammar

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
case class TokenNode(token: String, lemma: Option[String], posTag: Option[String], position: Int, edges: List[TokenEdge])
