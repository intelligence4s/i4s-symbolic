package i4s.symbolic.model

import i4s.symbolic.model.VariantType.VariantType

case class Concept(name: String, properties: List[Property])
case class Property(name: String, placeholder: Placeholder)

trait Placeholder {
  val name: String
  val variantType: VariantType = VariantType.String
}

case class Unqualified(override val name: String) extends Placeholder
case class Measurable(override val name: String) extends Placeholder
case class Enumerable(override val name: String) extends Placeholder

object VariantType extends Enumeration {
  type VariantType = Value
  val Decimal, Integer, String, Concept = Value
}
