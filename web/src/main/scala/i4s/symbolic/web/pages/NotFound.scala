package i4s.symbolic.web.pages

import i4s.symbolic.web.components.Banner
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._

@react object NotFound {
  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue  

  case class Props()
  val component = FunctionalComponent[Props] { props =>

    val notFound = div(className := "p-12 text-xl")("Sorry -- Resource not found")

    div(
      className := "h-screen flex flex-col",
      Banner(),
      notFound,
    )

  }
}
