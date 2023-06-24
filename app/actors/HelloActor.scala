package actors

import akka.actor._

object HelloActor {
  def props: Props = Props[HelloActor]()

  case class SayHello(name: String)
}

class HelloActor extends Actor with ActorLogging {
  import HelloActor._

  def receive: Receive = { case SayHello(name: String) =>
    sender() ! "Hello, " + name
  }
}
