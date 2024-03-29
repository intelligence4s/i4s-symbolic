package i4s.symbolic.web.components

import d3v4._

import i4s.symbolic.web.model.{JSTokenGraph, JSTokenNode, JSTokenEdge}
import org.scalajs.dom
import org.scalajs.dom.{SVGGElement, SVGTextElement}
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useEffect
import slinky.core.facade.React
import slinky.web.html._
import slinky.web.svg.{g, svg, className => svgClassName}


import scala.scalajs.js
import js.JSConverters._


@react object SentenceGraph {

  case class Props(graph: JSTokenGraph)
  case class Margins(top: Int, right: Int, bottom: Int, left: Int)

  val component = FunctionalComponent[Props] { props =>
    val containerRef = React.createRef[dom.svg.SVG] // Creates a reference to the SVG container d3 will draw into...
    val container = svg(ref := containerRef)(       // This creates the SVG container (note we specify the declared ref)
      g(svgClassName := "graph-area")               // this creates the G element in the SVG container
    )

    useEffect(() => {
      graphData(containerRef.current,props.graph)
    },Seq(props.graph))

    def graphData(svgRef: svg.tag.RefType, graph: JSTokenGraph): Unit = {
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
      val svg: d3selection.Selection[CurrentDom] = d3.select(svgRef)
        .attr("width","100%")
        .attr("height", s"${height}px")

      svg.selectAll(".graph-area").selectAll("*").remove()

      var accumulatedY = 0d
      val margin = 40
      val padding = 10

      var edges = List[JSTokenEdge]()

      graph.tokens.foreach(currToken =>
        currToken.edges.foreach(currTokenEdge => {
          currTokenEdge.source = currToken
          currTokenEdge.distance = (graph.tokens.indexOf(currToken) - graph.tokens.map(_.token).indexOf(graph.tokens(currTokenEdge.target).token)).abs
          edges = edges :+ currTokenEdge
        })
      )
      edges = edges.sortBy(_.distance)

      svg.select(".graph-area")
        .selectAll(".text-size")
        .data(edges.toJSArray)
        .enter
        .append("text")
        .text((d: JSTokenEdge) => d.relationship)
        .each{ (g: SVGTextElement, d: JSTokenEdge) =>
          d.width = g.getComputedTextLength() + padding
          d.targetRef = graph.tokens(d.target)
          d.offSet = (d.source.offSet + d.source.width/2 + d.targetRef.offSet + d.targetRef.width/2)/2 - d.width/2
          g.remove
        }

      val textSizing = svg
        .select(".graph-area")
        .selectAll(".text-size")
        .data(graph.tokens)
        .enter()
        .append("text")
        .text((d: JSTokenNode) => d.token)
        .each { (t: SVGTextElement, d: JSTokenNode) =>
          d.width = t.getComputedTextLength() + padding
          d.offSet = accumulatedY
          d.edges.indices.foreach(i =>
            if(d.edges(i).target < d.position){
              d.offSet = Math.max(d.offSet, graph.tokens(d.edges(i).target).offSet + d.edges(i).width)
            }
          )
          accumulatedY = d.offSet + d.width + margin
          t.remove()
        }

      edges.foreach(edge =>
        edge.offSet = (edge.source.offSet + edge.source.width/2 + edge.targetRef.offSet + edge.targetRef.width/2)/2 - edge.width/2
      )

      val selection = svg
        .select(".graph-area")
        .selectAll(".token")
        .data(graph.tokens)
        .enter()
        .append("g")
        .attr("transform", (d: JSTokenNode, i: Int) => s"translate(${d.offSet},2)")

      selection
        .append("rect")
        .attr("rx", 6)
        .attr("ry", 6)
        .attr("width", (d: JSTokenNode) => d.width)
        .attr("height", 20)
        .style("fill", "white")
        .style("stroke", "gray")

      selection
        .append("text")
        .text((d: JSTokenNode) => d.token)
        .attr("x", (d: JSTokenNode) => d.width/2)
        .attr("y", 10)
        .attr("dy", ".35em")
        .attr("text-anchor", "middle")
        .style("fill", "black")

      edges.indices.foreach(i => {
        var indices = (edges(i).source.position, edges(i).target)
        if(indices._1 > indices._2)
          indices = indices.swap
        for(j <- 0 until i){
          val otherIndices = (edges(j).source.position, edges(j).target)
          if((otherIndices._1 > indices._1 && otherIndices._1 < indices._2) || (otherIndices._2 > indices._1 && otherIndices._2 < indices._2)){
            if(edges(i).layer <= edges(j).layer){
              edges(i).layer = edges(j).layer + 1
            }
          }
        }
      })

      val relationships = svg.select(".graph-area")
        .selectAll(".relationship")
        .data(edges.toJSArray)
        .enter()
        .append("g")
        .attr("transform", (d: JSTokenEdge) => s"translate(${d.offSet - 5},${(d.layer + 1) * 25})")

      relationships
        .append("path")
        .attr("d", (d: JSTokenEdge) => d3.line().curve(d3.curveBasis).apply(List(
          ((d.width + 10)/2 - ((d.targetRef.offSet + d.targetRef.width/2) - (d.source.offSet + d.source.width/2)).abs/2, -25d * (d.layer + 1) + 22d),
          ((d.width + 10)/2 - ((d.targetRef.offSet + d.targetRef.width/2) - (d.source.offSet + d.source.width/2)).abs/2, 12.5d),
          ((d.width + 10)/2 + ((d.targetRef.offSet + d.targetRef.width/2) - (d.source.offSet + d.source.width/2)).abs/2, 12.5d),
          ((d.width + 10)/2 + ((d.targetRef.offSet + d.targetRef.width/2) - (d.source.offSet + d.source.width/2)).abs/2, -25d * (d.layer + 1) + 22d))
          .map(js.Tuple2.fromScalaTuple2(_)).toJSArray))
        .style("fill", "none")
        .style("stroke", "black")

      relationships
        .append("rect")
        .attr("x", 0)
        .attr("y", 0)
        .attr("width", (d: JSTokenEdge) => d.width + 10)
        .attr("height", 20)
        .attr("rx", 6)
        .attr("ry", 6)
        .style("fill", "white")
        .style("stroke", "gray")

      relationships
        .append("text")
        .text((d: JSTokenEdge) => {
          if(d.targetRef.position < d.source.position)
            "<" + d.relationship
          else
            d.relationship + ">"
        })
        .attr("x", (d: JSTokenEdge) => (d.width + 10)/2)
        .attr("y", 10)
        .attr("dy", ".35em")
        .attr("text-anchor", "middle")
        .style("fill", "black")

    }

    div(className := "flex p-10")(
      container
    )
  }
}
