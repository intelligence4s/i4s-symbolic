package i4s.symbolic.service

import akka.actor.typed.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import i4s.symbolic.service.servers.api.Server

object PlatformMain extends PlatformState with LazyLogging {

  def main(args: Array[String]): Unit = {
    implicit val sys: ActorSystem[_] = typedSystem

    logger.info(s"Starting Symbolic Server!")

    if (Server.isEnabled) {
      Server.init
      logger.info(s"RestServer now online. Please navigate to http://localhost:8080/graphql")
    }
  }
}
