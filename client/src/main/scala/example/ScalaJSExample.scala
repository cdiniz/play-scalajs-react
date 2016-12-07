package example
import example.components.Message
import example.services.ExampleCircuit
import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom
import scala.scalajs.js

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val wrapper = ExampleCircuit.connect(_.message)
    ReactDOM.render(wrapper(proxy => Message(proxy)), dom.document.getElementById("root"))
  }
}
