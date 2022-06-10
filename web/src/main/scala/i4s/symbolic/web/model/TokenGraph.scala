package i4s.symbolic.web.model

import scala.scalajs.js

case class TokenGraph(tokens: List[TokenNode]) {
  import js.JSConverters._

  def asJSTokenGraph(): JSTokenGraph = new JSTokenGraph(tokens.map(_.asJSTokenNode).toJSArray)
}

// @ScalaJSDefined
class JSTokenGraph(val tokens: js.Array[JSTokenNode]) extends js.Object

