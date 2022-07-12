package i4s.symbolic.service.servers.api

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.{Directives, Route}
import i4s.symbolic.service.servers.api.graphql.GraphqlRoutes

trait HasRoutes {
  def route(implicit system: ActorSystem[_]): Route
}

trait Routes extends HasRoutes {

  override def route(implicit system: ActorSystem[_]): Route =
    GraphqlRoutes.route
}
