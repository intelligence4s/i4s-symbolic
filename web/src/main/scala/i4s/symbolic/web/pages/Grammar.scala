package i4s.symbolic.web.pages

import i4s.symbolic.web.components.SentenceGraph
import i4s.symbolic.web.model.{TokenEdge, TokenGraph, TokenNode}
import i4s.symbolic.language.grammar.{TokenEdge, TokenGraph, TokenNode}
import i4s.symbolic.web.components.Banner
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.web.svg.{g, svg, className => svgClass}
import i4s.symbolic.web.components.Banner

@react object Grammar {

  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  val tokenList = List(
    TokenNode("I", Some("I"), Some("PRP"), 0, List.empty),
    TokenNode("like", Some("like"), Some("VBP"), 1, List(
      TokenEdge("nsubj", 0),
      TokenEdge("obj", 4),
      TokenEdge("obj", 6)
    )),
    TokenNode("my", Some("my"), Some("PRP$"), 2, List.empty),
    TokenNode("black", Some("black"), Some("JJ"), 3, List.empty),
    TokenNode("cat", Some("cat"), Some("NN"), 4, List(
      TokenEdge("nmod:poss", 2),
      TokenEdge("amod", 3)
    )),
    TokenNode(",", Some(","), Some(","), 5, List.empty),
    TokenNode("Sansa", Some("Sansa"), Some("NNP"), 6, List.empty),
    TokenNode(".", Some("."), Some("."), 7, List.empty),
  )


  val graph = TokenGraph(tokenList)

  case class Props()
  val component = FunctionalComponent[Props] { props =>
    val (sentence, setSentence) = useState[Option[String]](None)
    val (dependencyGraph, setDependencyGraph) = useState[List[String]](Nil)

    val jsTokenGraph = graph.toJs

    def dependencyPresentation: ReactElement =
      svg(svgClass := "h-96 w-full")(g(svgClass := "plot-area"),g(svgClass := "x-axis"),g(svgClass := "y-axis"))

    div(
      className := "h-screen flex flex-col",
      Banner(),
      SentenceGraph(graph = jsTokenGraph)
    )
  }
}