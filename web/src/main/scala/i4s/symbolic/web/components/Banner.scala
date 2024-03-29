package i4s.symbolic.web.components

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.web.html._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("resources/i4sSymbolic.svg", JSImport.Default)
@js.native
object TransparencyGlobalLogoWhite extends js.Object

@react class Banner extends StatelessComponent {
  type Props = Unit
  val logo = TransparencyGlobalLogoWhite.asInstanceOf[String]

  def render() = {
    div(
      div(
        className := "bg-black h-32",
        img(className := "py-5 m-auto md:ml-24 h-32", src := s"$logo", alt := "logo"),
      ),
      div(
        className := "bg-blue-500 h-1.5",
      ),
    )
  }
}
