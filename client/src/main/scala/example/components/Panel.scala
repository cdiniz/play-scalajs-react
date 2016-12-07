package example.components

import japgolly.scalajs.react.{ReactComponentB, ReactNode}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._


object Panel {

  case class Props(heading: String)

  val component = ReactComponentB[Props]("Panel")
    .renderPC((_, p, c) =>
      <.div(
        <.div(p.heading),
        <.div(c)
      )
    ).build

  def apply(props: Props, children: ReactNode*) = component(props, children: _*)
  def apply() = component
}

