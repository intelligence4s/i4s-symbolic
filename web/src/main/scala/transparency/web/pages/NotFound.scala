package transparency.web.pages

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._

@react object NotFound {
  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue  

  case class Props()
  val component = FunctionalComponent[Props] { props =>
    div(className := "NotFoundPage")("Not found")
  }
}
