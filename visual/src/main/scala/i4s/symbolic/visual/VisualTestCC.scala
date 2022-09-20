package i4s.symbolic.visual

import i4s.opencv.image.{Codecs, Image}
import i4s.opencv.image.constants._
import i4s.symbolic.console.{CommandConfig, HasCommandConfigs}
import org.bytedeco.opencv.opencv_core.{Mat, Size}

import java.io.File

object VisualTestCC extends HasCommandConfigs {
  import Image._
  import i4s.opencv.image.Ops._

  override def configs(): List[CommandConfig] = List(
    CommandConfig("^(testcv)$".r, values => runCvTest(), "testcv")
  )

  private def detectShapes(): Unit = {
    val testFile = new File(getClass.getClassLoader.getResource("shapes_and_colors.jpeg").getFile)

    for {
      image <- Codecs.readImage(testFile)
    } yield {
      println(image.description)

      val ratio = 300f / image.size().width()
      val resized = image.resize(new Size(),ratio,ratio,InterpolationFlags.Linear)
      println(resized.description)

      val grayImage = resized.cvtColor(ColorConversionCodes.BGR2Gray)
      println(grayImage.description)

      val blurred = grayImage.gaussianBlur(new Size(5,5),0)

      val (threshed,_) = blurred.threshold(60,255,Set(ThresholdTypes.Binary))

      val contours = threshed.findContours(RetrievalModes.External,ContourApproximationMethods.Simple)

      println(s"${contours.size()} contours found!")
    }
  }

  private def runCvTest(): Boolean = {
    detectShapes()
    false
  }
}
