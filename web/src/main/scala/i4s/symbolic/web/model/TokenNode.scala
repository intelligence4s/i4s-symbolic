package i4s.symbolic.web.model

import scala.scalajs.js

case class TokenNode(token: String, edges: List[TokenEdge]) {
  import js.JSConverters._

  def asJSTokenNode(): JSTokenNode = new JSTokenNode(token, edges.map(_.asJSTokenEdge()).toJSArray)
}

// @ScalaJSDefined
class JSTokenNode(val token: String, edges: js.Array[JSTokenEdge]) extends js.Object
