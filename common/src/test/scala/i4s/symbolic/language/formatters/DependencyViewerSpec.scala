package i4s.symbolic.language.formatters

import org.clulab.struct.{DirectedGraph, Edge}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DependencyViewerSpec extends AnyWordSpec with Matchers {
  val words = Array("I", "like", "my", "black", "cat", ",", "Sansa", ".")
  val sansa = DirectedGraph[String](List(
    Edge(1, 0, "nsubj"),
    Edge(1, 4, "obj"),
    Edge(1, 6, "obj"),
    Edge(4, 3, "amod"),
    Edge(4, 2, "nmod:poss")), Some(8))
  val words2 = Array("This", "graph", "looks", "the", "way", "it", "should", ".")
  val test = DirectedGraph[String](List(
    Edge(1, 0, "det"),
    Edge(2, 1, "nsubj"),
    Edge(2, 4, "obj"),
    Edge(4, 3, "det"),
    Edge(4, 6, "acl:relcl"),
    Edge(6, 5, "nsubj")), Some(8))

  "DependencyViewer" should {
    "print formatted dependency graphs" in {
      var viewer = new DependencyViewer(sansa, words)
      println(viewer.layout)
      viewer = new DependencyViewer(test, words2)
      println(viewer.layout)
    }
  }

  "DependencyViewer" should {
    "correctly format a dependency graph" in {
      val viewer = new DependencyViewer(sansa, words)
      val lines = viewer.layout.split("\n")
      var count = 0
      var indexes = new Array[Int](0)
      for(i <- 0 until lines.length){
        if(lines(i).matches("[A-Za-z,.?!;:]+ *[a-z: |<-]*")){
          count += 1
          indexes = indexes :+ i
        }
      }
      assert(count==sansa.size)
      for(i <- 0 until sansa.size){
        val outgoingEdges = sansa.edges.filter(_.source == i)
        val incomingEdges = sansa.edges.filter(_.destination == i)
        if(incomingEdges.length == 0){
          for(j <- 0 until outgoingEdges.length){
            assert(lines(indexes(i) - j).matches("[A-Za-z,.?!;:]*[ /]*-+\\|[-|]*[a-z:]+[ |]*"))
          }
        }
        else{
          for(j <- 0 until outgoingEdges.length){
            if(!lines(indexes(i) - j - 1).matches(" +/-+\\|[a-z:]+[ |]*"))
              println(lines(indexes(i) - j - 1))
            assert(lines(indexes(i) - j - 1).matches(" +/-+\\|[a-z:]+[ |]*"))
          }
          for(j <- 0 until incomingEdges.length){
            assert(lines(indexes(i) + j).contains("-"))
          }
        }
      }
    }
  }

  "DependencyViewer" should {
    "build correct graphs" in {
      var viewer = new DependencyViewer(sansa, words)
      assert(viewer.layout.equals("I <-------|\n          |\n     /----|nsubj\n    /--------------|obj\nlike --------------|----------|obj\n                   |          |\nmy <---------|     |          |\n             |     |          |\nblack <---|  |     |          |\n          |  |     |          |\n    /-----|amod    |          |\n   /---------|nmod:poss       |\ncat <--------------|          |\n                              |\n,                             |\n                              |\nSansa <-----------------------|\n\n."))
      viewer = new DependencyViewer(test, words2)
      assert(viewer.layout.equals("This <-----|\n           |\n      /----|det\ngraph <----|\n           |\n      /----|nsubj\nlooks ---------|obj\n               |\nthe <----|     |\n         |     |\n    /----|det  |\n   /-----------||acl:relcl\nway <----------||\n                |\nit <--------|   |\n            |   |\n       /----|nsubj\nshould <--------|\n\n."))
    }
  }
}
