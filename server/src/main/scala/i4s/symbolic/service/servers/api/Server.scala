package i4s.symbolic.service.servers.api

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.Http
import akka.{Done, actor}
import i4s.symbolic.service.servers.{ServerLike, Stoppable}

import scala.concurrent.{ExecutionContext, Future}

object Server extends ServerLike with Routes {
  private val serverConfig = ServerConfig.config

  override def isEnabled: Boolean = serverConfig.enabled

  override def init(implicit typedSystem: ActorSystem[_]): Future[Stoppable] = {
    implicit val system: actor.ActorSystem = typedSystem.toClassic
    start
  }

  private def start(implicit system: actor.ActorSystem): Future[Stoppable] = {
    implicit val ec: ExecutionContext = system.dispatcher

    Http().newServerAt(serverConfig.interface, serverConfig.port).bind(route(system.toTyped)).map { binding =>
      new Stoppable {
        override def stop(): Future[Done] = binding.unbind()
      }
    }
  }

}
