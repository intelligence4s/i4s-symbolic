package i4s.symbolic.web.model

import scala.scalajs.js

case class TokenEdge(relationship: String, target: TokenNode) {
  def asJSTokenEdge(): JSTokenEdge = new JSTokenEdge(relationship, target.asJSTokenNode())
}

// @ScalaJSDefined
class JSTokenEdge(val relationship: String, val target: JSTokenNode) extends js.Object {
  var source : JSTokenNode = null
  var targetRef : JSTokenNode = null
  var distance: Int = -1
  var layer: Int = 0
  var offSet: Double = -1
  var width: Double = -1

  def asTokenEdge(): TokenEdge = TokenEdge(relationship, target.asTokenNode())
}

