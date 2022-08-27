package i4s.scalacv.image

import i4s.scalacv.core.types.{MatTypes, Types}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.File

class CodecsSpec extends AnyWordSpec with Matchers {

  "Codecs" should {
    "reading a jpg" in {

      val testFile = new File(getClass.getClassLoader.getResource("shapes_and_colors.jpeg").getFile)
      val maybeImage = Codecs.readImage(testFile)
      maybeImage shouldBe defined
      val image = maybeImage.get

      image.dims() shouldBe 2
      image.dataType shouldBe Types.Cv8U
      image.matType shouldBe MatTypes.Cv8UC3
      image.channels shouldBe 3
      image.total shouldBe 336600
    }
  }

}
