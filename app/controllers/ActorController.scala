package controllers

import actors.HelloActor
import akka.actor.{ActorRef, ActorSystem}
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ActorController @Inject() (system: ActorSystem, cc: ControllerComponents)
    extends AbstractController(cc) {

  private val helloActor: ActorRef =
    system.actorOf(HelloActor.props, "hello-actor")
  implicit val timeout: Timeout = 5.seconds

  def sayHello(name: String): Action[AnyContent] = Action.async {
    (helloActor ? HelloActor.SayHello(name)).mapTo[String].map { message =>
      Ok(message)
    }
  }
}
