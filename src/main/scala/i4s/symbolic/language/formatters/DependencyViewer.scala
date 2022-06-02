package i4s.symbolic.language.formatters

import org.clulab.struct.DirectedGraph

class DependencyViewer(val graph: DirectedGraph[String], val words: Array[String]) {
  var graphString = ""

  private val lineCount = graph.size * 3
  private val lines = Array.fill[String](lineCount)("")

  // gets line number from node index
  private def nodeLine(nodeIdx: Int):Int = nodeIdx*3 + 1

  private def generateString(): Unit = {

    // print out node names, create map of which nodes have incoming and outgoing edges
    var nodeNum = 0
    val hasInOut = new Array[(Boolean, Boolean)](graph.size)
    for (lineNum <- 1 to lineCount by 3) {
      lines(lineNum) = "(" + words(nodeNum) + ")"
      hasInOut(nodeNum) = (graph.getIncomingEdges(nodeNum).length > 0, graph.getOutgoingEdges(nodeNum).length > 0)
      nodeNum += 1
    }

    // gather simplest edges with no crossovers into a set
    nodeNum = 0
    var edgeSet = graph.edges.toSet
    for(edge <- edgeSet) {
      if(edge.source < edge.destination) {
        if(hasInOut(edge.source)._2) edgeSet -= edge
        for(node <- edge.source + 1 until edge.destination) {
          if(hasInOut(node)._1 || hasInOut(node)._2) edgeSet -= edge
        }
      } else {
        for(node <- edge.source - 1 until edge.destination by -1) {
          if(hasInOut(node)._1 || hasInOut(node)._2) edgeSet -= edge
        }
      }
    }

    // diagram simplest edges
    for(edge <- edgeSet) {

      lines(nodeLine(edge.source) - 1) += "  /"
      lines(nodeLine(edge.destination)) += "<" + edge.relation + "-"

      var longestLength = 0
      val lineRange = (math.min(nodeLine(edge.source), nodeLine(edge.destination)),
        math.max(nodeLine(edge.source), nodeLine(edge.destination)))

      for(lineIdx <- lineRange._1 to lineRange._2) {
        if (lines(lineIdx).length > longestLength) longestLength = lines(lineIdx).length
      }

      lines(nodeLine(edge.source) - 1) += "-" * (longestLength - lines(nodeLine(edge.source) - 1).length) + "+"
      lines(nodeLine(edge.destination)) += "-" * (longestLength - lines(nodeLine(edge.destination)).length) + "+"

      for(lineIdx <- lineRange._1 until lineRange._2) {
        while(lines(lineIdx).length < longestLength) lines(lineIdx) += " "
        if (lines(lineIdx).length < longestLength + 1) lines(lineIdx) += "|"
      }
    }

    edgeSet = graph.edges.toSet -- edgeSet
  }

  generateString()
  for(line <- lines) graphString ++= line + "\n"
}
