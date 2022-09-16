package i4s.scalacv.core.model.mats

import i4s.scalacv.core.model.Scalar
import i4s.scalacv.core.types.Types
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MappedMatSpec extends AnyWordSpec with Matchers {
  "MappedMat" should {

    import i4s.scalacv.core.model.mats.syntax._

    "Put/Get Scalar values to/from a Byte Map" in {
      val mat = MappedMat[Scalar,Int](50,50,Some(Types.Cv8U),Some(3))

    }

    "Put/Get Scalar values to/from a Double Map" in {
      val mat = MappedMat[Scalar,Double](50,50,Some(Types.Cv64F),Some(3))

    }
  }
}
