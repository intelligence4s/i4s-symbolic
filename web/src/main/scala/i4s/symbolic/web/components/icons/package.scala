package i4s.symbolic.web.components

import slinky.core.facade.ReactElement
import slinky.web.svg.{className => svgClass, d, fill, path, stroke, strokeLinecap, strokeLinejoin, strokeWidth, svg, viewBox}
import slinky.web.html._

import scala.scalajs.js

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

  def PlayIcon(classText: String): ReactElement = {
    svg(svgClass := s"$classText", fill := "none", viewBox := "0 0 24 24", stroke := "currentColor", strokeWidth := "2")(
      path(strokeLinecap := "round", strokeLinejoin := "round", d := "M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"),
      path(strokeLinecap := "round", strokeLinejoin := "round", d := "M21 12a9 9 0 11-18 0 9 9 0 0118 0z"),
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
