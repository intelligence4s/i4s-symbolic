package i4s.symbolic.web.model

import scala.scalajs.js

// @ScalaJSDefined
class JSTokenEdge(val relationship: String, val target: Int) extends js.Object{
  var source : JSTokenNode = null
  var targetRef : JSTokenNode = null
  var distance: Int = -1
  var layer: Int = 0
  var offSet: Double = -1
  var width: Double = -1
}

