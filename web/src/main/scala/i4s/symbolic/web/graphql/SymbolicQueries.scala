package i4s.symbolic.web.graphql

import caliban.client.{CalibanClientError, SelectionBuilder}
import i4s.symbolic.language.grammar.{TokenEdge, TokenGraph, TokenNode}
import sttp.capabilities
import sttp.client3._

import scala.concurrent.Future
import scala.language.implicitConversions

trait SymbolicQueryResult

object SymbolicQueries {
  import monix.execution.Scheduler.Implicits.global
  private val serverUrl = uri"/graphql"

  implicit def responseToList[T](response: Future[Response[Either[CalibanClientError, List[T]]]]): Future[List[T]] =
    response.map(_.body.toOption.getOrElse(Nil))

  implicit def responseToOption[T](response: Future[Response[Either[CalibanClientError, Option[T]]]]): Future[Option[T]] =
    response.map(_.body.toOption.flatten)

  def grammarFor(sentence: String): Future[List[TokenGraph]] = {
    val backend: SttpBackend[Future, capabilities.WebSockets] = FetchBackend()

    val query = Symbolic.Query.grammar(statement = sentence)(graphSelection)
    query.toRequest(serverUrl).send(backend)
  }

  val edgeSelection: SelectionBuilder[Symbolic.TokenEdge,TokenEdge] =
    (Symbolic.TokenEdge.relationship ~ Symbolic.TokenEdge.target).mapN(TokenEdge)

  val nodeSelection: SelectionBuilder[Symbolic.TokenNode,TokenNode] =
    (Symbolic.TokenNode.token ~
      Symbolic.TokenNode.lemma ~
      Symbolic.TokenNode.posTag ~
      Symbolic.TokenNode.position ~
      Symbolic.TokenNode.edges(edgeSelection)).mapN(TokenNode)

  val graphSelection: SelectionBuilder[Symbolic.TokenGraph,TokenGraph] =
    Symbolic.TokenGraph.tokens(nodeSelection).map(TokenGraph)
}
