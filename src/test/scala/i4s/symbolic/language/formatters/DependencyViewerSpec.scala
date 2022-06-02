package i4s.symbolic.language.formatters

import org.clulab.struct.{DirectedGraph, Edge}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DependencyViewerSpec extends AnyWordSpec with Matchers{
  "a DependencyViewer implementation" should {
    "create a string visual graph of Sansa-related sentence" in {
      val testGraph = DirectedGraph[String](List(
        Edge(1, 0, "nsub"),
        Edge(1, 4, "obj"),
        Edge(1, 6, "obj"),
        Edge(4, 3, "amod"),
        Edge(4, 2, "nmod:poss")))
      val target = " v-nsub-+\n" +
        "(0)     |\n" +
        "        |\n" +
        "        |\n" +
        "(1)-----^----v---v\n" +
        "             |   |\n" +
        " v-nmod:poss-|-+ |\n" +
        "(2)          | | |\n" +
        "             | | |\n" +
        " v-amod-+    | | |\n" +
        "(3)     |    | | |\n" +
        "        |    | | |\n" +
        " v-obj--|----+ | |\n" +
        "(4)-----^------^ |\n" +
        "                 |\n" +
        "                 |\n" +
        "(5)              |\n" +
        "                 |\n" +
        " v-obj-----------+\n" +
        "(6)\n" +
        "\n" +
        "\n" +
        "(7)"
    }
  }
}
