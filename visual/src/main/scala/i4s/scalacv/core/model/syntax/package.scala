package i4s.scalacv.core.model
import i4s.scalacv.core.types.Types
import i4s.scalacv.core.types.Types.Type

package object syntax {

  implicit val byteMatable = new ByteMatable {
    override def depth: Type = Types.Cv8S
  }

  implicit val charMatable = new CharMatable {
    override def depth: Type = Types.Cv16U
  }

  implicit val doubleMatable = new DoubleMatable {
    override def depth: Type = Types.Cv64F
  }

  implicit val floatMatable = new FloatMatable {
    override def depth: Type = Types.Cv32F
  }

  implicit val intMatable = new IntMatable {
    override def depth: Type = Types.Cv32S
  }

  implicit val shortMatable = new ShortMatable {
    override def depth: Type = Types.Cv16S
  }

  implicit val unsignedShortMatable = new UnsignedShortMatable {
    override def depth: Type = Types.Cv16U
  }

  implicit val unsignedByteMatable = new UnsignedByteMatable {
    override def depth: Type = Types.Cv8U
  }

  implicit val pointMatable = new PointMatable {
    override def depth: Type = Types.Cv32S
  }

  implicit val scalarMatable = new ByteScalarMatable {
    override def depth: Type = Types.Cv8S
  }
}
