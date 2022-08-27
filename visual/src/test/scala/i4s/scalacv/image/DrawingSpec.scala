package i4s.scalacv.image

import i4s.scalacv.core.types.MatTypes
import org.bytedeco.opencv.opencv_core.Point
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DrawingSpec extends AnyWordSpec with Matchers {

  "Drawing" should {
    import Drawing._

    "support drawing circles" in {
      val image = new Image(30,30,MatTypes.Cv8UC3)

    }
  }

}
