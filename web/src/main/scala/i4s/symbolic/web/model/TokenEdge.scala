package i4s.symbolic.web.model

import scala.scalajs.js

case class TokenEdge(relationship: String, target: TokenNode) {
  def asJSTokenEdge(): JSTokenEdge = new JSTokenEdge(relationship, target.asJSTokenNode())
}

// @ScalaJSDefined
class JSTokenEdge(val relationship: String, target: JSTokenNode) extends js.Object

