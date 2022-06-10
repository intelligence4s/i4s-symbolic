package i4s.symbolic.web.components

import d3v4._
import i4s.symbolic.web.model.{DataRecord, JSDataRecord, TokenGraph}
import org.scalajs.dom
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useEffect
import slinky.core.facade.React
import slinky.web.html._
import slinky.web.svg.{g, svg, className => svgClassName}

import scala.scalajs.js

@react object SentenceGraph {

  case class Props(graph: TokenGraph)
  case class Margins(top: Int, right: Int, bottom: Int, left: Int)

  val component = FunctionalComponent[Props] { props =>
    val containerRef = React.createRef[dom.svg.SVG] // Creates a reference to the SVG container d3 will draw into...
    val container = svg(ref := containerRef)(       // This creates the SVG container (note we specify the declared ref)
      g(svgClassName := "graph-area")               // this creates the G element in the SVG container
    )

    useEffect(() => {
      graphData(containerRef.current,props.graph)
    },Seq(props.graph))

    def graphData(svgRef: svg.tag.RefType, graph: TokenGraph): Unit = {
      // In here we will...
      // a) select the G component with the class ".graph-area"
      // b) draw a box with rounded corners for each token in the graph
      // c) draw the relationships between tokens on lines above and below the tokens
      // d) draw paths (lines / vectors) from the source token, through the relationship and to the target token
      // Look at the code in BarChart to understand how this is done.
      // In d3, you create elements bound to data like this:
      // svg.selectAll(".token") // selects all currently drawn g elements (which will be zero to start)
      //   .data(graph.tokens)   // loads the data collection for the following behavior
      //   .append("rect")       // creates a "rect" graph element for every token in graph.tokens and associates the data
      //   .style(...)           // several "style" statements to style the rect
      //   .attr(...)            // set the attribytes (x, y, width, height, etc) for each element.
      //                         // note that the calls to attr and style include a "data" element containng the token data to graph
      // START WITH A LINE OF TOKENS, embellish and style from there.
    }

    div(className := "flex")(
      container
    )
  }
}