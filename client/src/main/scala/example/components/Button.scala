package example.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object Button {

  case class Props(onClick: Callback)

  val component = ReactComponentB[Props]("Button")
    .renderPC((_, p, c) =>
      <.button(^.tpe := "button", ^.onClick --> p.onClick, c)
    ).build

  def apply(props: Props, children: ReactNode*) = component(props, children: _*)
  def apply() = component
}
