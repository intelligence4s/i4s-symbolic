package i4s.symbolic.web.pages

import i4s.symbolic.web.components.{Banner, SentenceGraph, icons}
import i4s.symbolic.web.graphql.SymbolicQueries
import i4s.symbolic.web.model.JSTokenGraph
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.{useEffect, useState}
import slinky.core.facade.ReactElement
import slinky.web.html._

@react object Language {
  import i4s.symbolic.web.model.syntax._

  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  case class Props()
  val component = FunctionalComponent[Props] { props =>
    val (sentence, setSentence) = useState[Option[String]](None)
    val (dependencyGraph, setDependencyGraph) = useState[Option[JSTokenGraph]](None)
    val (loading, setLoading) = useState[Boolean](true)

    useEffect(() => sentence.foreach { s =>
      SymbolicQueries.grammarFor(s).map { graphList =>
        setDependencyGraph(graphList.map(_.toJs).headOption)
      }
    }, Seq(sentence))

    val graphOrWaitElement: ReactElement = if (loading) {
      icons.LoadingIcon
    } else {
      dependencyGraph
        .map(jsTokenGraph => SentenceGraph(graph = jsTokenGraph).asInstanceOf[ReactElement])
        .getOrElse(div(className := "flex p-10"))
    }

    div(
      className := "h-screen flex flex-col",
      Banner(),

      div(className := "flex p-4")(
        div(className := "flex w-full bg-gray-200 border rounded")(
          input(`type` := "text", className := "appearance-none block w-full bg-gray-200 text-gray-700 border rounded my-2 ml-2 py-1 px-4 leading-tight focus:outline-none focus:bg-white", placeholder := "Sentence to analyze...")(),
          a(className := "flex-shrink-0 text-sm text-gray-500 border rounded hover:bg-gray-500 hover:text-white p-1 m-auto mx-1", href := "#")(
            icons.PlayIcon("w-6 h-6 m-auto")
          )
        )
      ),
      div(className := "flex p-4")(
        graphOrWaitElement
      )
    )
  }
}
