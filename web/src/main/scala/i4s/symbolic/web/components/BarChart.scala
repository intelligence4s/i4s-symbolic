package i4s.symbolic.web.components

import d3v4._
import i4s.symbolic.web.model.{DataRecord, JSDataRecord}
import org.scalajs.dom
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useEffect
import slinky.core.facade.React
import slinky.web.html._
import slinky.web.svg.{g, svg, className => svgClassName}

import scala.scalajs.js

@react object BarChart {
  import js.JSConverters._

  case class Props(name: String, data: List[DataRecord])
  case class Margins(top: Int, right: Int, bottom: Int, left: Int)

  val component = FunctionalComponent[Props] { props =>
    val containerRef = React.createRef[dom.svg.SVG]
    val container = svg(ref := containerRef)(
      g(svgClassName := "plot-area"),
      g(svgClassName := "x-axis"),
      g(svgClassName := "y-axis")
    )

    useEffect(() => {
      val records = props.data.map(_.asJSDataRecord()).toJSArray
      graphData(containerRef.current,records)
    },Seq(props.data))

    def graphData(svgRef: svg.tag.RefType, data: js.Array[JSDataRecord]): Unit = {
      val width = 800
      val height = 500
      val margin = Margins(20,30,30,40)

      val xBand = d3
        .scaleBand()
        .domain(data.map((d) => d.year))
        .rangeRound(js.Array(margin.left, width - margin.right))
        .padding(0.1)

      val yBand = d3
        .scaleLinear()
        .domain(js.Array(0,data.maxBy(_.sales).sales))
        .rangeRound(js.Array(height - margin.bottom, margin.top))

      //Color for start
      val c = d3color.rgb("darkslateblue")

      val rectXFun = (d: JSDataRecord) => xBand(d.year)
      val rectWidthFun = (d: JSDataRecord) => xBand.bandwidth()
      val rectYFun = (d: JSDataRecord) => yBand(d.sales)
      val rectHeightFun = (d: JSDataRecord) => yBand(0) - yBand(d.sales)

      val rectColorFun: (JSDataRecord, Int) => String = (_: JSDataRecord, i: Int) => {
        c.opacity = 1 - (i.toFloat) * 0.02
        c.toString
      }

      val extent = d3.extent(xBand.domain())

      val svg: d3selection.Selection[CurrentDom] = d3.select(svgRef)
        .attr("width","100%")
        .attr("height", s"${height}px")

      svg
        .select(".x-axis")
        .attr("transform", s"translate(0,${height - margin.bottom})")
        .call(
          d3axis
            .axisBottom(xBand)
            .tickValues(
              d3
                .ticks(extent(0),extent(1),width/40)
                .filter(x => !js.isUndefined(xBand(x)))
            )
            .tickSizeOuter(0)
        )

      svg
        .select(".y-axis")
        .attr("transform", s"translate(${margin.left},0)")
        .style("color","steelblue")
        .call(d3axis.axisLeft(yBand).ticks(5,"s"))
        .call((g: d3selection.Selection[CurrentDom]) => g.select(".domain").remove())
        .call((g: d3selection.Selection[CurrentDom]) => g
          .append("text")
          .attr("y", 10)
          .attr("fill", "currentColor")
          .attr("text-anchor", "end")
          .attr("transform", "scale(1.6)")
          .text("Sales")
        )

      val sel = svg
        .select(".plot-area")
        .attr("color","blue")
        .selectAll(".bar")
        .data(data)

      sel.enter()
        .append("rect")
        .attr("x", rectXFun)
        .attr("y", rectYFun)
        .attr("width", rectWidthFun)
        .attr("height", rectHeightFun)
        .style[String]("fill", rectColorFun)
    }

    div(className := "flex")(
      container
    )
  }
}
