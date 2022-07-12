package i4s.symbolic.service.servers.api.graphql

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{Directives, Route}
import i4s.symbolic.service.servers.api.HasRoutes
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.http.akka.circe.CirceHttpSupport
import sangria.marshalling.circe._

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object GraphqlRoutes extends HasRoutes with CorsSupport with CirceHttpSupport {
  import Directives._

  override def route(implicit system: ActorSystem[_]): Route = corsHandler {
    implicit val ec: ExecutionContextExecutor = system.executionContext

    path("graphql") {
      graphQLPlayground ~
      prepareGraphQLRequest {
        case Success(req) =>
          val graphqlResponse = Executor.execute(
            schema = SchemaDefinition.schema,
            queryAst = req.query,
            variables = req.variables,
            operationName = req.operationName
          ).map(OK -> _)
            .recover {
              case error: QueryAnalysisError => BadRequest -> error.resolveError
              case error: ErrorWithResolver => InternalServerError -> error.resolveError
            }

          complete(graphqlResponse)
        case Failure(preparationError) => complete(BadRequest,formatError(preparationError))
      }
    }
  }
}
