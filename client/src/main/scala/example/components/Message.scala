package example.components

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import example.services.UpdateMessage

/**
  * This is a simple component demonstrating how to display async data coming from the server
  */
object Message {

  // create the React component to hold a message
  val Message = ReactComponentB[ModelProxy[Pot[String]]]("Message")
    .render_P { proxy =>
      Panel(Panel.Props("Greeting message from server:"),
        // render messages depending on the state of the Pot
        proxy().renderPending(_ > 500, _ => <.p("Loading...")),
        proxy().renderFailed(ex => <.p("Failed to load")),
        proxy().render(m => <.p(m)),
        Button(Button.Props(proxy.dispatchCB(UpdateMessage())), " Update")
      )
    }
    .componentDidMount(scope =>
      // update only if Message is empty
      Callback.when(scope.props.value.isEmpty)(scope.props.dispatchCB(UpdateMessage()))
    )
    .build

  def apply(proxy: ModelProxy[Pot[String]]) = Message(proxy)
}
