package i4s.symbolic.service

import akka.actor
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Props}

trait PlatformState {
  lazy val system: actor.ActorSystem = PlatformStateSingleton.system
  lazy val typedSystem: ActorSystem[_] = PlatformStateSingleton.typedSystem

  def spawn[T](behavior: Behavior[T], name: String, props: Props = Props.empty): ActorRef[T] = PlatformStateSingleton.spawn(behavior,name,props)
  def spawnAnonymous[T](behavior: Behavior[T], props: Props = Props.empty): ActorRef[T] = PlatformStateSingleton.spawnAnonymous(behavior,props)
}

object PlatformStateSingleton extends PlatformStateSingleton

private[service] trait PlatformStateSingleton {
  import akka.actor.typed.scaladsl.adapter._

  lazy val system: actor.ActorSystem = akka.actor.ActorSystem("aletheia")
  lazy val typedSystem: ActorSystem[_] = system.toTyped

  def spawn[T](behavior: Behavior[T], name: String, props: Props = Props.empty): ActorRef[T] = system.spawn(behavior,name,props)
  def spawnAnonymous[T](behavior: Behavior[T], props: Props = Props.empty): ActorRef[T] = system.spawnAnonymous(behavior,props)
}