package i4s.scalacv.image
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class ImageSpec extends AnyWordSpec with Matchers {
  "Image" should {
    "create an empty placeholder" in {
      val empty = Image()

    }
  }

}
