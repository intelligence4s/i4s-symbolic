package i4s.symbolic.web.pages

import i4s.symbolic.language.grammar.{TokenGraph, TokenNode}
import i4s.symbolic.web.components.{Banner, SentenceGraph}
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.web.svg.{g, svg, className => svgClass}

@react object Grammar {
  import i4s.symbolic.web.model.syntax._

  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  val tokenList = List(
    TokenNode("I", 0, List.empty),
    TokenNode("like", 1, List.empty),
    TokenNode("my", 2, List.empty),
    TokenNode("black", 3, List.empty),
    TokenNode("cat", 4, List.empty),
    TokenNode(",", 5, List.empty),
    TokenNode("Sansa", 6, List.empty),
    TokenNode(".", 7, List.empty),
  )

  val graph = TokenGraph(tokenList)

  case class Props()
  val component = FunctionalComponent[Props] { props =>
    val (sentence, setSentence) = useState[Option[String]](None)
    val (dependencyGraph, setDependencyGraph) = useState[List[String]](Nil)

    val jsTokenGraph = graph.toJs()

    def dependencyPresentation: ReactElement =
      svg(svgClass := "h-96 w-full")(g(svgClass := "plot-area"),g(svgClass := "x-axis"),g(svgClass := "y-axis"))

    div(
      className := "h-screen flex flex-col",
      Banner(),
      SentenceGraph(graph = jsTokenGraph)
    )
  }
}