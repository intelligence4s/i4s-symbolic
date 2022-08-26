package i4s.scalacv.image

import i4s.scalacv.core.types.MatTypes
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ColorsSpec extends AnyWordSpec with Matchers {

  "Colors" should {
    "support color conversion" in {
      val image = new Image(300,300,MatTypes.Cv8UC3)

      val buffer = image.asBuffer()

      val x = image.asByteBuffer()


      println(image.description)


    }

  }

}
