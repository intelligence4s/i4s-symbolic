package i4s.symbolic.language.formatters

import org.clulab.struct.{DirectedGraph, Edge}

class DependencyViewer(val graph: DirectedGraph[String], val words: Array[String]) {
  private var graphString = ""

  private val lineCount = graph.size * 3
  private val lines = Array.fill[String](lineCount)("")

  // gets line number from node index
  private def nodeLine(nodeIdx: Int):Int = nodeIdx*3 + 1

  // creates an array of strings where each index represents 1 line of the ASCII graph
  private def generateStringArray(): Unit = {

    // print out node names, create map of which nodes have incoming and outgoing edges
    var nodeNum = 0
    val inOutCount = new Array[(Int, Int)](graph.size)
    for (lineNum <- 1 to lineCount by 3) {
      lines(lineNum) = "(" + words(nodeNum) + ")"
      inOutCount(nodeNum) = (graph.getIncomingEdges(nodeNum).length, graph.getOutgoingEdges(nodeNum).length)
      nodeNum += 1
    }

    // set of ungraphed edges
    var edgeSet = graph.edges.toSet
    var priorityMap = Map[Int, scala.collection.mutable.Set[Edge[String]]]()

    // create priority ordering map for the edges
    nodeNum = 0
    for(edge <- edgeSet) {
      var priorityNum = 0
      if(edge.source < edge.destination) {
        priorityNum += inOutCount(edge.source)._1 + inOutCount(edge.destination)._2
        for(node <- edge.source + 1 until edge.destination) {
          priorityNum += inOutCount(node)._1 + inOutCount(node)._2
        }
      } else {
        for(node <- edge.source - 1 until edge.destination by -1) {
          priorityNum += inOutCount(node)._1 + inOutCount(node)._2
        }
      }

      if(priorityMap contains priorityNum) priorityMap(priorityNum) += edge
      else priorityMap += (priorityNum -> scala.collection.mutable.Set(edge))
    }

    val prioVal = priorityMap.keys.toList.sorted

    // add first edge arrow to each node
    for(priority <- prioVal) {
      for(edge <- priorityMap(priority)) {
        if(lines(nodeLine(edge.destination)) == "(" + words(edge.destination) + ")")
          lines(nodeLine(edge.destination)) += "<" + edge.relation + "-"
        edgeSet -= edge
      }
    }

    // add edges to string in order of priority
    for(priority <- prioVal) {
      for(edge <- priorityMap(priority)) {

        if(lines(nodeLine(edge.source) - 1).isEmpty) lines(nodeLine(edge.source) - 1) += "  /"
        if(edgeSet contains edge) lines(nodeLine(edge.destination)) += "<" + edge.relation + "-"

        var longestLength = 0
        val lineRange = (math.min(nodeLine(edge.source), nodeLine(edge.destination)),
          math.max(nodeLine(edge.source), nodeLine(edge.destination)))

        for(lineIdx <- lineRange._1 - 1 to lineRange._2) {
          if (lines(lineIdx).length > longestLength) longestLength = lines(lineIdx).length
        }

        longestLength += 1

        lines(nodeLine(edge.source) - 1) += "-" * (longestLength - lines(nodeLine(edge.source) - 1).length) + "+"
        lines(nodeLine(edge.source) - 1) =
          "  " + lines(nodeLine(edge.source) - 1).trim.replace(" ", "-")
        lines(nodeLine(edge.destination)) += "-" * (longestLength - lines(nodeLine(edge.destination)).length) + "+"
        lines(nodeLine(edge.destination)) = lines(nodeLine(edge.destination)).trim.replace(" ", "-")

        for(lineIdx <- lineRange._1 until lineRange._2) {
          while(lines(lineIdx).length < longestLength) lines(lineIdx) += " "
          if (lines(lineIdx).length < longestLength + 1) lines(lineIdx) += "|"
        }
        edgeSet -= edge
      }
    }
  }

  generateStringArray()
  for(line <- lines) graphString ++= line + "\n"

  def getGraph: String = graphString
}
