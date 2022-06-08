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
        Edge(4, 2, "nmod:poss")),
        Some(8))
      val testGraphWords = Array("I", "like", "my", "black", "cat", ",", "Sansa", ".")

      val target = "\n" +
        "(I)<nsub--+\n" +
        "          |\n" +
        "  /-------+-------+-+\n" +
        "(like)            | |\n" +
        "                  | |\n" +
        "                  | |\n" +
        "(my)<nmod:poss--+ | |\n" +
        "                | | |\n" +
        "                | | |\n" +
        "(black)<amod--+ | | |\n" +
        "              | | | |\n" +
        "  /-----------+-+ | |\n" +
        "(cat)<obj---------+ |\n" +
        "                    |\n" +
        "                    |\n" +
        "(,)                 |\n" +
        "                    |\n" +
        "                    |\n" +
        "(Sansa)<obj---------+\n" +
        "\n" +
        "\n" +
        "(.)\n\n"

      val viewer = new DependencyViewer(testGraph, testGraphWords)

      viewer.getGraph should be <= target
    }
    "create a string visual graph of woodchuck sentence" in {
      val testWords = Array("How", "much", "wood", "could", "a", "woodchuck", "chuck", "if", "a", "woodchuck", "could",
        "chuck", "wood", "?")
      val testGraph = DirectedGraph[String](List(
        Edge(1, 0, "advmod"),
        Edge(2, 1, "amod"),
        Edge(5, 4, "det"),
        Edge(6, 2, "obj"),
        Edge(6, 3, "aux"),
        Edge(6, 5, "nsubj"),
        Edge(6, 11, "advcl_if"),
        Edge(9, 8, "det"),
        Edge(11, 7, "mark"),
        Edge(11, 9, "nsubj"),
        Edge(11, 10, "aux"),
        Edge(11, 12, "obj")),
        Some(14))

      val target = "\n" +
        "(How)<advmod---+\n" +
        "               |\n" +
        "  /------------+\n" +
        "(much)<amod--+\n" +
        "             |\n" +
        "  /----------+\n" +
        "(wood)<obj---------------+\n" +
        "                         |\n" +
        "                         |\n" +
        "(could)<aux------------+ |\n" +
        "                       | |\n" +
        "                       | |\n" +
        "(a)<det------------+   | |\n" +
        "                   |   | |\n" +
        "  /----------------+   | |\n" +
        "(woodchuck)<nsubj----+ | |\n" +
        "                     | | |\n" +
        "  /------------------+-+-+-+\n" +
        "(chuck)                    |\n" +
        "                           |\n" +
        "                           |\n" +
        "(if)<mark---------------+  |\n" +
        "                        |  |\n" +
        "                        |  |\n" +
        "(a)<det------------+    |  |\n" +
        "                   |    |  |\n" +
        "  /----------------+    |  |\n" +
        "(woodchuck)<nsubj-----+ |  |\n" +
        "                      | |  |\n" +
        "                      | |  |\n" +
        "(could)<aux-------+   | |  |\n" +
        "                  |   | |  |\n" +
        "  /---------------+-+-+-+  |\n" +
        "(chuck)<advcl_if----|------+\n" +
        "                    |\n" +
        "                    |\n" +
        "(wood)<obj----------+\n" +
        "\n" +
        "\n" +
        "(?)\n\n"

      val viewer = new DependencyViewer(testGraph, testWords)

      viewer.getGraph should be <= target
    }
  }
}
