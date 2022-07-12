package i4s.symbolic.service.servers.api.managers

import i4s.symbolic.language.grammar.{TokenEdge, TokenGraph, TokenNode}
import org.clulab.processors.{Processor, Sentence}
import org.clulab.processors.corenlp.CoreNLPProcessor

object LanguageManager {

  private lazy val proc:Processor = new CoreNLPProcessor()

  def tokenGraphFor(sentence: String): List[TokenGraph] = {
    val doc = proc.annotate(sentence)

    val sentences = doc.sentences.toList
    sentences.map(sentence2TokenGraph)
  }

  private def sentence2TokenGraph(sentence: Sentence): TokenGraph = {
    val nodes = {
      val size = sentence.raw.length
      val emptyArray = Array.fill[Option[String]](size)(None)
      val posTags = sentence.tags.map(_.map(Option(_))).getOrElse(emptyArray)
      val lemmas = sentence.lemmas.map(_.map(Option(_))).getOrElse(emptyArray)

      val edges = sentence.dependencies.map { dependencies =>
        dependencies.outgoingEdges.map(edges => edges.toList.map { case (target, relationship) => TokenEdge(relationship, target) })
      }.getOrElse(Array.fill[List[TokenEdge]](size)(List.empty))

      sentence.raw.zip(lemmas).zip(posTags).zip(edges).zipWithIndex.map { case ((((token, lemma), posTag), edges), position) =>
        TokenNode(token, lemma, posTag, position, edges)
      }
    }

    TokenGraph(nodes.toList)
  }
}
