package i4s.symbolic.web.pages

import i4s.symbolic.language.grammar.TokenGraph
import i4s.symbolic.web.components.{Banner, SentenceGraph, icons}
import i4s.symbolic.web.graphql.SymbolicQueries
import i4s.symbolic.web.model.JSTokenGraph
import org.scalajs.dom.{Event, KeyboardEvent, html}
import slinky.core.{FunctionalComponent, SyntheticEvent}
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
    val (dependencyGraph, setDependencyGraph) = useState[Option[TokenGraph]](None)
    val (loading, setLoading) = useState[Boolean](false)

    useEffect(() => sentence.foreach { s =>
      println(s"""Sending "$s" to server for analysis...""")

      SymbolicQueries.grammarFor(s).map { graphList =>
        println(s"Result from query -- ${graphList.headOption}")
        setDependencyGraph(graphList.headOption)
      }
    }, Seq(sentence))

    val graphOrWaitElement: ReactElement = {
      if (loading) {
        div(className := "flex mx-4 border rounded")(icons.LoadingIcon)
      } else {
        if (dependencyGraph.isDefined) {
          div(className := "flex mx-4 bg-blue-200 boerder rounded")(
            dependencyGraph
            .map(tokenGraph => SentenceGraph(graph = tokenGraph.toJs).withKey("sentence-graph"))
            .getOrElse(div(className := "flex p-10"))
          )
        } else {
          div(className := "flex mx-4 boerder rounded")
        }
      }
    }

    def handleAnalyzeClick(e: SyntheticEvent[html.Anchor, Event]): Unit = {
      val input = e.currentTarget.parentElement.querySelector("#sentence-input").asInstanceOf[html.Input]
      analyzeSentence(input.value)
    }

    def handleAnalizeEnterKey(e: SyntheticEvent[html.Input, KeyboardEvent]): Unit = {
      val input = e.currentTarget
      if (e.nativeEvent.key == "Enter") {
        analyzeSentence(input.value)
      }
    }

    def analyzeSentence(sentenceText: String): Unit = {
      println(s"Analyzing = ${sentenceText}")
      if (!loading) setSentence(Some(sentenceText))
    }

    div(
      className := "h-screen flex flex-col",
      Banner(),

      div(className := "flex p-4")(
        div(className := "flex w-full bg-gray-200 border rounded")(
          input(`type` := "text", id := "sentence-input", className := "appearance-none block w-full bg-gray-200 text-gray-700 border rounded my-2 ml-2 py-1 px-4 leading-tight focus:outline-none focus:bg-white", placeholder := "Sentence to analyze...", onKeyUp := (handleAnalizeEnterKey(_)))(),
          a(className := "flex-shrink-0 text-sm text-gray-500 border rounded hover:bg-gray-500 hover:text-white p-1 m-auto mx-1", href := "#", onClick := (handleAnalyzeClick(_)))(
            icons.PlayIcon("w-6 h-6 m-auto")
          )
        )
      ),
      graphOrWaitElement
    )
  }
}
