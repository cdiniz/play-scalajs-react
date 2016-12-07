package example.services

import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import shared.Api
import autowire._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue


// Actions
case class UpdateMessage(potResult: Pot[String] = Empty) extends PotAction[String, UpdateMessage] {
  override def next(value: Pot[String]) = UpdateMessage(value)
}

// The base model of our application
case class RootModel(message: Pot[String])

/**
  * Handles actions related to the Message Component
  *
  * @param modelRW Reader/Writer to access the model
  */
class MessageHandler[M](modelRW: ModelRW[M, Pot[String]]) extends ActionHandler(modelRW) {
  implicit val runner = new RunAfterJS

  override def handle = {
    case action: UpdateMessage =>
      val updateMessageFuture = action.effect(Client[Api].greet("ScalaJS").call())(_.message)
      action.handleWith(this, updateMessageFuture)(PotAction.handler())
  }
}

// Application circuit
object ExampleCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected def initialModel = RootModel(Empty)
  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new MessageHandler(zoomRW(_.message)((m, v) => m.copy(message = v)))
  )
}