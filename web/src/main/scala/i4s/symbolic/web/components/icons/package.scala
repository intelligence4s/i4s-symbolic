package i4s.symbolic.web.components

import slinky.core.facade.ReactElement
import slinky.web.svg.{className => svgClass, d, fill, path, stroke, strokeLinecap, strokeLinejoin, strokeWidth, svg, viewBox}
import slinky.web.html._

import scala.scalajs.js

import scala.scalajs.js.annotation.JSImport

package object icons {
  def SearchIcon(classText: String): ReactElement = {
    svg(svgClass := s"$classText", fill := "none", viewBox:= "0 0 24 24", stroke := "currentColor", strokeWidth := "2")(
      path(strokeLinecap := "round", strokeLinejoin := "round",d := "M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z")
    )
  }

  def CancelIcon(classText: String): ReactElement = {
    svg(svgClass := s"$classText", fill := "none", viewBox:= "0 0 24 24", stroke := "currentColor", strokeWidth := "2")(
      path(strokeLinecap := "round", strokeLinejoin := "round",d := "M6 18L18 6M6 6l12 12")
    )
  }

  def LoadingIcon: ReactElement = {
    div(
      className := "flex flex-grow justify-center items-center",
      div(
        style := js.Dynamic.literal(borderTopColor = "transparent"),
        className := "w-20 h-20 border-4 border-blue-400 border-solid rounded-full animate-spin",
      )
    )
  }
}
