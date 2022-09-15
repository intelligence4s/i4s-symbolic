package i4s.scalacv.core.model
import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types.Type

package object mats {
  implicit val byteMatable: Matable[Byte] = new PrimitiveMatable[Byte] {
    override val depth: Type = Types.Cv8S
  }

  implicit val charMatable: Matable[Char] = new PrimitiveMatable[Char] {
    override val depth: Type = Types.Cv16U
  }

  implicit val doubleMatable: Matable[Double] = new PrimitiveMatable[Double] {
    override val depth: Type = Types.Cv64F
  }

  implicit val floatMatable: Matable[Float] = new PrimitiveMatable[Float] {
    override def depth: Type = Types.Cv32F
  }

  implicit val intMatable: Matable[Int] = new PrimitiveMatable[Int] {
    override def depth: Type = Types.Cv32S
  }

  implicit val shortMatable: Matable[Short] = new PrimitiveMatable[Short] {
    override def depth: Type = Types.Cv16S
  }

/*
  implicit val pointMatable: PointMatable = new PointMatable {
    override def depth: Type = Types.Cv32S
  }

  implicit val byteScalarMatable: ByteScalarMatable = new ByteScalarMatable {
    override def depth: Type = Types.Cv8S
  }
*/

}
