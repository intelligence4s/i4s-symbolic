package i4s.symbolic.service.servers

import akka.Done

import scala.concurrent.Future

trait Stoppable {
  def stop(): Future[Done]
}
