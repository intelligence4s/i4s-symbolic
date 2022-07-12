package i4s.symbolic.service.servers.api.graphql

import i4s.symbolic.language.grammar.{TokenEdge, TokenGraph, TokenNode}
import i4s.symbolic.service.servers.api.managers.LanguageManager
import sangria.schema._
import sangria.macros.derive._

object SchemaDefinition {
  implicit val tokenEdgeType: ObjectType[Unit, TokenEdge] = deriveObjectType[Unit,TokenEdge](
    ObjectTypeDescription("Token node representing a token in a grammar.")
  )

  implicit val tokenNodeType: ObjectType[Unit, TokenNode] = deriveObjectType[Unit,TokenNode](
    ObjectTypeDescription("Token node representing a token in a grammar.")
  )

  implicit val tokenGraphType: ObjectType[Unit, TokenGraph] = deriveObjectType[Unit,TokenGraph](
    ObjectTypeDescription("Token graph representing parts of speech and dependencies for a sentence.")
  )

  private val statement = Argument("statement", StringType)

  private val queryType = ObjectType("Query", fields[Unit,Unit](
    Field("grammar", ListType(tokenGraphType),
      description = Some("Returns a grammar graph from the given statement."),
      arguments = statement :: Nil,
      resolve = c => LanguageManager.tokenGraphFor(c arg statement)
    )
  ))

  val schema: Schema[Unit, Unit] = Schema(queryType)
}
