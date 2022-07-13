package i4s.symbolic.service.servers

import akka.actor.typed.ActorSystem

import scala.concurrent.Future

trait ServerLike {
  def isEnabled: Boolean

  def init(implicit system: ActorSystem[_]): Future[Stoppable]
}
