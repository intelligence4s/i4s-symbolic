package i4s.symbolic

import i4s.symbolic.language.pos.{ClauseTag, GrammarTag, PartOfSpeechTag, PhraseTag}
import org.clulab.struct.{NonTerminal, Terminal, Tree}

package object nlp {
  import PartOfSpeechTag._
  import ClauseTag._
  import PhraseTag._

  sealed trait Grammar

  case class Root(statements: List[Grammar]) extends Grammar

  case class Statement(tag: ClauseTag, phrases: List[Grammar]) extends Grammar

  case class Phrase(tag: PhraseTag, parts: List[Grammar]) extends Grammar

  case class Token(tag: PartOfSpeechTag, token: String, lemma: String, chunk: String, namedEntity: String, normalizedEntity: String) extends Grammar

  implicit class TreeSupport(sourceTree: Tree) {

    def describe(layer: Int = 0): String = {
      val formatted =
        sourceTree match {
          case Terminal(token) =>
            s" + $token"
          case NonTerminal(tag, children) =>
            val pos = PartOfSpeechTag.values.find(_.name == tag).map(_.name)
              .orElse(ClauseTag.values.find(_.name == tag).map(_.name))
              .orElse(PhraseTag.values.find(_.name == tag).map(_.name))
            s" + ${pos.getOrElse(tag)}\n${children.map(ctree => new TreeSupport(ctree).describe(layer + 1)).mkString("\n")}"
        }

      s"${" | " * layer}$formatted"
    }

    def grammar(tokenMap: Map[String, Token]): Option[Grammar] = extractGrammar(tokenMap, None)

    private def extractGrammar(tokenMap: Map[String, Token], parentTag: Option[GrammarTag]): Option[Grammar] =
      sourceTree match {
        case Terminal(token) =>
          parentTag match {
            case Some(posTag: PartOfSpeechTag.Value) => tokenMap.get(token)
            case _ => throw new IllegalArgumentException(s"Unexpected Terminal grammar tree node ${sourceTree} with missing or invalid parent tag - $parentTag")
          }
        case NonTerminal(tagString, children) =>
          PartOfSpeechTag.values.find(_.name == tagString).flatMap(pos => children.headOption.flatMap(tree => new TreeSupport(tree).extractGrammar(tokenMap, Some(pos)))) orElse
            PhraseTag.values.find(_.name == tagString).map(pos => Phrase(pos, children.toList.flatMap(tree => new TreeSupport(tree).extractGrammar(tokenMap, Some(pos))))) orElse
            ClauseTag.values.find(_.name == tagString).map(pos => Statement(pos, children.toList.flatMap(tree => new TreeSupport(tree).extractGrammar(tokenMap, Some(pos)))))
              .orElse {
                tagString match {
                  case "ROOT" =>
                    Some(Root(children.toList.flatMap(tree => new TreeSupport(tree).extractGrammar(tokenMap, None))))
                  case _ => throw new IllegalArgumentException(s"Unexpected tag string $tagString encountered in grammar tree!")
                }
              }
      }
  }
}
