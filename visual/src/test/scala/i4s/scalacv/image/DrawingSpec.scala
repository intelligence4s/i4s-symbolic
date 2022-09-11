package i4s.scalacv.image

import i4s.scalacv.core.constants.AccessFlags
import i4s.scalacv.core.model.{Mat, Point, Rect, Scalar}
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
      val image = new Image(300,300,MatTypes.Cv8UC3,Scalar.White)

      val red: Scalar = Scalar.Red
      val rectangle = Rect(Point(30,30),Point(270,270))

      image.rectangle(rectangle,red,LineTypes.Line4,-1,0)
      image.write(new File("red-square.png"))
      
      val closed = image.show("Red box")
      Await.ready(closed,1.minute)
    }

    "support drawing circles" in {
      val image = new Image(150,150,MatTypes.Cv8UC3)
      image.circle(Point(75,75),30,Scalar.Red)

      val imageWillClose = image.show("Burnin Ring-o-fire")
      val expectedWillClose = redCircleImage.show("I am your father")
      val bothClosed = Future.sequence(List(imageWillClose,expectedWillClose))
      Await.ready(bothClosed,1.minute)

      //      image.write(new File("red-circle.png"))

      val imageBuffer = Using.resource(new OpenCVFrameConverter.ToMat()) { openCVConverter =>
        Using.resource(openCVConverter.convert(image))(_.image.head)
      }

      val expectedBuffer = Using.resource(new OpenCVFrameConverter.ToMat()) { openCVConverter =>
        Using.resource(openCVConverter.convert(redCircleImage))(_.image.head)
      }

      val imageMat = image
      val buffer: Buffer = image.createBuffer()

      println(buffer.sizeOf[Byte])
      println(buffer.lazyListOf[Byte].size)
      println(expectedBuffer.lazyListOf[Byte].sum)

      imageBuffer.lazyListOf[Byte].zip(expectedBuffer.lazyListOf[Byte])
        .take(buffer.sizeOf[Byte])
        .foreach { case (value,expected) =>
          if (value != expected) {
            println(s"$value is not $expected")
          }
          value shouldBe expected
        }
    }
  }

}
