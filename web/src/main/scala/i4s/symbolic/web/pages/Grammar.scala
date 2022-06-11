package i4s.symbolic.web.pages

import i4s.symbolic.web.components.{Banner, SentenceGraph}
import i4s.symbolic.web.model.{DataRecord, TokenEdge, TokenGraph, TokenNode}
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.web.svg.{g, svg, className => svgClass}

@react object Grammar {
  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  val tokenList = List(
    TokenNode("I", List.empty),
    TokenNode("like", List.empty),
    TokenNode("my", List.empty),
    TokenNode("black", List.empty),
    TokenNode("cat", List.empty),
    TokenNode(",", List.empty),
    TokenNode("Sansa", List.empty),
    TokenNode(".", List.empty),
  )
  tokenList(1).edges = List(
    TokenEdge("nsubj", tokenList(0)),
    TokenEdge("obj", tokenList(4)),
    TokenEdge("obj", tokenList(6))
  )
  tokenList(4).edges = List(
    TokenEdge("amod", tokenList(3)),
    TokenEdge("nmod:poss", tokenList(2)),
  )

  val graph = TokenGraph(tokenList)

  case class Props()
  val component = FunctionalComponent[Props] { props =>
    val (sentence, setSentence) = useState[Option[String]](None)
    val (dependencyGraph, setDependencyGraph) = useState[List[String]](Nil)

    def dependencyPresentation: ReactElement =
      svg(svgClass := "h-96 w-full")(g(svgClass := "plot-area"),g(svgClass := "x-axis"),g(svgClass := "y-axis"))

    div(
      className := "h-screen flex flex-col",
      Banner(),
      SentenceGraph(graph = graph)
    )
  }
}