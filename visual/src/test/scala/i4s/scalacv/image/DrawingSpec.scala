package i4s.scalacv.image

import com.google.common.hash.{HashCode, Hashing}
import i4s.scalacv.core.constants.AccessFlags
import i4s.scalacv.core.model.mats.BaseMat
import i4s.scalacv.core.model.{Point, Rect, Scalar}
import i4s.scalacv.core.types.MatTypes
import i4s.scalacv.image.constants.LineTypes
import org.bytedeco.javacv.OpenCVFrameConverter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.File
import java.nio.{Buffer, ByteBuffer}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Using

class DrawingSpec extends AnyWordSpec with Matchers {
  import i4s.common.syntax._

  val redCircleImage: Image = {
    val file = new File(getClass.getClassLoader.getResource("red-circle.png").getFile)
    Codecs.readImage(file).get
  }

  "Drawing" should {
    import Codecs._
    import Drawing._
    import i4s.scalacv.image.ui.ViewMaster._

    "support filling a rectangle" in {
      val expectedHash = HashCode.fromString("481edfc4ecea1d71a842818435266bb7a43777eb6d2bdc0e4ec5e0c67c337756")
      val image = Image(300,300,3,Scalar.White)

      val red: Scalar = Scalar.Red
      val rectangle = Rect(Point(30,30),Point(270,270))
      image.rectangle(rectangle,red,LineTypes.Line4,-1,0)

      val hash = image.rawValues.foldLeft(Hashing.sha256.newHasher)(_.putInt(_)).hash
      hash shouldBe expectedHash

//      image.write(new File("red-square.png"))
//      val closed = image.show("Red box")
//      Await.ready(closed,1.minute)
    }

    "support drawing circles" in {
      val expected = HashCode.fromString("8b62391244665e3d57fa2d2425c54aa7f5418b1fbad6c4566146a62dd85833ae")
      val image = Image(150,150,3,Scalar.Black)
      image.circle(Point(75,75),30,Scalar.Red)

      val hash = image.rawValues.foldLeft(Hashing.sha256.newHasher)(_.putInt(_)).hash
      hash shouldBe expected

//      image.write(new File("red-circle.png"))
//
//      val imageWillClose = image.show("actual")
//      val expectedWillClose = redCircleImage.show("expected")
//      val bothClosed = Future.sequence(List(imageWillClose,expectedWillClose))
//      Await.ready(bothClosed,1.minute)

    }
  }

}
