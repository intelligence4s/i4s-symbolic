package i4s.symbolic.web.pages

import i4s.symbolic.web.components.{Banner, BarChart}
import i4s.symbolic.web.model.DataRecord
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.web.svg.{g, svg, className => svgClass}

@react object Grammar {
  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

  val data = List(
    DataRecord(1980,24.3,8949000),
    DataRecord(1985,27.6,10979000),
    DataRecord(1990,28,9303000),
    DataRecord(1991,28.4,8185000),
    DataRecord(1992,27.9,8213000),
    DataRecord(1993,28.4,8518000),
    DataRecord(1994,28.3,8991000),
    DataRecord(1995,28.6,8620000),
    DataRecord(1996,28.5,8479000),
    DataRecord(1997,28.7,8217000),
    DataRecord(1998,28.8,8085000),
    DataRecord(1999,28.3,8638000),
    DataRecord(2000,28.5,8778000),
    DataRecord(2001,28.8,8352000),
    DataRecord(2002,29,8042000),
    DataRecord(2003,29.5,7556000),
    DataRecord(2004,29.5,7483000),
    DataRecord(2005,30.3,7660000),
    DataRecord(2006,30.1,7762000),
    DataRecord(2007,31.2,7562000),
    DataRecord(2008,31.5,6769000),
    DataRecord(2009,32.9,5402000),
    DataRecord(2010,33.9,5636000),
    DataRecord(2011,33.1,6093000),
    DataRecord(2012,35.3,7245000),
    DataRecord(2013,36.4,7586000),
    DataRecord(2014,36.5,7708000),
    DataRecord(2015,37.2,7517000),
    DataRecord(2016,37.7,6873000),
    DataRecord(2017,39.4,6081000),
  )

  case class Props()
  val component = FunctionalComponent[Props] { props =>
    val (sentence, setSentence) = useState[Option[String]](None)
    val (dependencyGraph, setDependencyGraph) = useState[List[String]](Nil)

    def dependencyPresentation: ReactElement =
      svg(svgClass := "h-96 w-full")(g(svgClass := "plot-area"),g(svgClass := "x-axis"),g(svgClass := "y-axis"))

    div(
      className := "h-screen flex flex-col",
      Banner(),
      BarChart(name = "Chuck", data = data)
    )
  }
}