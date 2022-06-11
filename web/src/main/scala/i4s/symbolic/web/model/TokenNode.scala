package i4s.symbolic.web.model

import scala.scalajs.js

case class TokenNode(token: String, var edges: List[TokenEdge]) {
  import js.JSConverters._

  def asJSTokenNode(): JSTokenNode = new JSTokenNode(token, edges.map(_.asJSTokenEdge()).toJSArray)
}

// @ScalaJSDefined
class JSTokenNode(val token: String, val edges: js.Array[JSTokenEdge]) extends js.Object {
  var offSet: Double = -1
  var width: Double = -1
}

