package i4s.symbolic.web

import i4s.symbolic.language.grammar.{TokenEdge, TokenGraph}

import scala.scalajs.js.JSConverters._

package object model {
  object syntax {
    implicit class JsTokenGraphConversion(tokenGraph: TokenGraph) {

      def tokenEdgeToJsTokenEdge(tokenEdge: TokenEdge): JSTokenEdge =
        new JSTokenEdge(tokenEdge.relationship, tokenEdge.target)

      def toJs: JSTokenGraph = {
        val jsTokens = tokenGraph.tokens.map { token =>
          new JSTokenNode(token.token, token.position, token.edges.map(tokenEdgeToJsTokenEdge).toJSArray)
        }

        new JSTokenGraph(jsTokens.toJSArray)
      }
    }
  }
}
