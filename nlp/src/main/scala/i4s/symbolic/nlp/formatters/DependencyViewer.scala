package i4s.symbolic.nlp.formatters

import org.clulab.struct.DirectedGraph

class DependencyViewer(graph: DirectedGraph[String], words: Array[String]){

  def layout: String = {
    var layoutList = new Array[Array[String]](0)
    for(i <- 0 until graph.size){
      layoutList = layoutList :+ nodeLines(i)
    }
    //val outputList = layoutList.map(_.mkString)
    val lineList = stylize(layoutList).mkString("\n\n").split("\n")
    createVerticals(lineList).mkString("\n")
  }

  def nodeLines(node: Int): Array[String] = {
    val incomingEdges = graph.edges.filter(_.destination == node)
    val outgoingEdges = graph.edges.filter(_.source == node)
    val edges = outgoingEdges ++ incomingEdges
    val formattedList = Array.fill[String](edges.length)("")
    for(i <- 0 until edges.length -1){
      var j = i
      while(j <= words(node).length){
        formattedList(i) += " "
        j = j + 1
      }
      if(edges(i).source == node){
        formattedList(i) += "/"
        formattedList(i) += "-----"*(node - edges(i).destination).abs
        }
      else{
        formattedList(i) += "<"
        formattedList(i) += "-----"*(node - edges(i).source).abs
      }
      //formattedList(i) += "\n"
    }
    if(formattedList.length == 0){
      return Array.fill[String](1)(words(node)/* + "\n"*/)
    }
    formattedList(formattedList.length-1) += words(node) + " "
    if(edges.last.destination == node){
      formattedList(formattedList.length-1) += "<"
      formattedList(formattedList.length-1) += "-----"*(node - edges.last.source).abs
      formattedList(formattedList.length-1) = formattedList.last.substring(0, formattedList.last.length-1)
    }
    else
      formattedList(formattedList.length-1) += "-----"*(node - edges.last.destination).abs
    //formattedList(formattedList.length-1) += "\n"
    formattedList
  }

  def stylize(skeleton: Array[Array[String]]): Array[String] = {
    for(currentNode <- 0 until graph.size){
      val incomingEdges = graph.edges.filter(_.destination == currentNode)
      val outgoingEdges = graph.edges.filter(_.source == currentNode)
      val edges = outgoingEdges ++ incomingEdges
      for(currentEdge <- edges.indices){
        val destination = edges(currentEdge).destination
        val source = edges(currentEdge).source
        var indexAtOtherEnd = 0
        if(destination != currentNode) {
          val destinationEdges = graph.edges.filter(_.source == destination) ++ graph.edges.filter(_.destination == destination)
          for(i <- destinationEdges.indices){
            if(destinationEdges(i).source == currentNode){
              indexAtOtherEnd = i
            }
          }
          if(skeleton(currentNode)(currentEdge).length < skeleton(edges(currentEdge).destination)(indexAtOtherEnd).length){
            skeleton(currentNode)(currentEdge) += "-"*(skeleton(edges(currentEdge).destination)(indexAtOtherEnd).length - skeleton(currentNode)(currentEdge).length)
          }
        }
        else{
          val sourceEdges = graph.edges.filter(_.source == source) ++ graph.edges.filter(_.destination == source)
          for(i <- sourceEdges.indices){
            if(sourceEdges(i).destination == currentNode){
              indexAtOtherEnd = i
            }
          }
          if(skeleton(currentNode)(currentEdge).length < skeleton(edges(currentEdge).source)(indexAtOtherEnd).length){
            skeleton(currentNode)(currentEdge) += "-"*(skeleton(edges(currentEdge).source)(indexAtOtherEnd).length - skeleton(currentNode)(currentEdge).length)
          }
        }
      }
    }
    skeleton.map(_.mkString("\n"))
  }

  def createVerticals(lines: Array[String]) : Array[String] = {
    var indexes = new Array[Int](0)
    for(i <- lines.indices){
      if(lines(i).matches("[A-Za-z,.?!;:]+ *[</-]*")){
        indexes  = indexes :+ i
      }
    }
    for(currentNode <- 0 until graph.size){
      val edges = graph.edges.filter(_.source == currentNode)
      var edgeVerticals = new Array[Int](0)
      for(currentEdge <- edges.indices){
        val destination = edges(currentEdge).destination
        var source = 0
        if(!graph.edges.exists(_.destination == currentNode)){
          source = indexes(currentNode) - (edges.length - 1) + currentEdge
        }
        else
          source = indexes(currentNode) - edges.length + currentEdge
        var index = 0
        for(i <- graph.edges.filter(_.destination == destination).indices){
          if(graph.edges.filter(_.destination == destination)(i).source == currentNode){
            index = indexes(destination) + i
          }
        }
        var currIndex = source
        val indexOfVertical = lines(source).lastIndexOf("-")
        edgeVerticals = edgeVerticals :+ indexOfVertical
        for(i <- 0 to (index - source).abs){
          if(index < source){
            if(lines(currIndex).length < lines(source).length){
              for(j <- 0 until lines(source).length - lines(currIndex).length){
                lines(currIndex) += " "
              }
            }
            lines(currIndex) = lines(currIndex).substring(0, indexOfVertical) + "|" + lines(currIndex).substring(indexOfVertical + 1)
            currIndex -= 1
          }
          else {
            if(lines(currIndex).length < lines(source).length){
              for(j <- 0 until lines(source).length - lines(currIndex).length){
                lines(currIndex) += " "
              }
            }
            lines(currIndex) = lines(currIndex).substring(0, indexOfVertical) + "|" + lines(currIndex).substring(indexOfVertical + 1)
            currIndex += 1
          }
        }
      }
      for(currentEdge <- edges.indices){
        val destination = edges(currentEdge).destination
        var source = 0
        if(!graph.edges.exists(_.destination == currentNode)){
          source = indexes(currentNode) - (edges.length - 1) + currentEdge
        }
        else
          source = indexes(currentNode) - edges.length + currentEdge
        var index = 0
        for(i <- graph.edges.filter(_.destination == destination).indices){
          if(graph.edges.filter(_.destination == destination)(i).source == currentNode){
            index = indexes(destination) + i
          }
        }
        if(lines(source).length < (lines(source).substring(0, edgeVerticals(currentEdge) + 1) + edges(currentEdge).relation).length)
          lines(source) = lines(source).substring(0, edgeVerticals(currentEdge) + 1) + edges(currentEdge).relation
        else
          lines(source) = lines(source).substring(0, edgeVerticals(currentEdge) + 1) + edges(currentEdge).relation + lines(source).substring(edgeVerticals(currentEdge) + 1 + edges(currentEdge).relation.length)
      }
    }
    lines
  }
}
