package i4s.symbolic

import org.clulab.struct.{NonTerminal, Terminal, Tree}
import i4s.symbolic.language.pos.{ClauseTag, GrammarTag, PartOfSpeechTag, PhraseTag}

import scala.annotation.tailrec

package object language {
  sealed trait Grammar

  case class Root(statements: List[Grammar]) extends Grammar
  case class Statement(tag: ClauseTag, phrases: List[Grammar]) extends Grammar
  case class Phrase(tag: PhraseTag, parts: List[Grammar]) extends Grammar
  case class Token(tag: PartOfSpeechTag, token: String) extends Grammar

  implicit class TreeSupport(sourceTree: Tree) {

    def describe(layer: Int = 0): String = {
      val formatted =
        sourceTree match {
          case Terminal(token) =>
            s" + $token"
          case NonTerminal(tag,children) =>
            val pos = PartOfSpeechTag.withNameOption(tag)
              .orElse(ClauseTag.withNameOption(tag))
              .orElse(PhraseTag.withNameOption(tag))
            s" + ${pos.getOrElse(tag)}\n${children.map(ctree => new TreeSupport(ctree).describe(layer + 1)).mkString("\n")}"
        }

      s"${" | "*layer}$formatted"
    }

    def grammar(): Option[Grammar] = extractGrammar(None)

    private def extractGrammar(parentTag: Option[GrammarTag]): Option[Grammar] =
      sourceTree match {
        case Terminal(token) =>
          parentTag match {
            case Some(posTag: PartOfSpeechTag) =>
              Some(Token(posTag,token))
            case _ => throw new IllegalArgumentException(s"Unexpected Terminal grammar tree node ${sourceTree} with missing or invalid parent tag - $parentTag")
          }
        case NonTerminal(tagString,children) =>
          PartOfSpeechTag.withNameOption(tagString).flatMap(pos => children.headOption.flatMap(tree => new TreeSupport(tree).extractGrammar(Some(pos)))) orElse
          PhraseTag.withNameOption(tagString).map(pos => Phrase(pos, children.toList.flatMap(tree => new TreeSupport(tree).extractGrammar(Some(pos))))) orElse
          ClauseTag.withNameOption(tagString).map(pos => Statement(pos, children.toList.flatMap(tree => new TreeSupport(tree).extractGrammar(Some(pos)))))
          .orElse {
            tagString match {
              case "ROOT" =>
                Some(Root(children.toList.flatMap(tree => new TreeSupport(tree).extractGrammar(None))))
              case _ => throw new IllegalArgumentException(s"Unexpected tag string $tagString encountered in grammar tree!")
            }
          }
      }
  }
}
