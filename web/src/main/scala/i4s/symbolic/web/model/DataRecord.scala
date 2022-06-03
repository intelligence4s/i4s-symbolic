package i4s.symbolic.web.model

import scala.scalajs.js
import scala.scalajs.js.annotation._

case class DataRecord(year: Int, efficiency: Double, sales: Int) {
  def asJSDataRecord(): JSDataRecord = new JSDataRecord(year,efficiency,sales)
}

// @ScalaJSDefined
class JSDataRecord(val year: Int, val efficiency: Double, val sales: Int) extends js.Object 

