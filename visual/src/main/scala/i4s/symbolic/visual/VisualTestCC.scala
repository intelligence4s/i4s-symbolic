package i4s.symbolic.visual

import i4s.scalacv.core.types.{FlowTypes, Types}
import i4s.scalacv.image.Codecs
import i4s.scalacv.image.constants.{ColorConversionCodes, ContourApproximationMethods, InterpolationFlags, RetrievalModes, ThresholdTypes}
import i4s.symbolic.console.{CommandConfig, HasCommandConfigs}
import org.bytedeco.opencv.opencv_core.{Mat, Size}

import java.io.File

object VisualTestCC extends HasCommandConfigs {
  import i4s.scalacv.image.Colors._
  import i4s.scalacv.image.Filters._
  import i4s.scalacv.image.Transforms._
  import i4s.scalacv.image.Shapes._

  override def configs(): List[CommandConfig] = List(
    CommandConfig("^(testcv)$".r, values => runCvTest(), "test")
  )

  private def detectShapes(): Unit = {
    val testFile = new File(getClass.getClassLoader.getResource("shapes_and_colors.jpeg").getFile)

    for {
      image <- Codecs.readImage(testFile)
    } yield {
      val ratio = 300f / image.size().width()
      val resized: Mat = image.resize(new Size(),ratio,ratio,InterpolationFlags.Linear)

      val grayImage = resized.cvtColor(ColorConversionCodes.BGR2Gray)
      val blurred = grayImage.gaussianBlur(new Size(5,5),0)
      val (threshed,_) = blurred.threshold(60,255,Set(ThresholdTypes.Binary))

      val contours = threshed.findContours(RetrievalModes.External,ContourApproximationMethods.Simple)

      /*
            resize(image,resized,new Size(),ratio,ratio,InterpolationFlags.Linear)

            val grayImage: Mat = new Mat(image.size(), FlowTypes.Ft8UC1)
            cvtColor(image, grayImage, CV_BGR2GRAY)

            val blurred = new Mat(resized.size(), CV_8UC1)
            GaussianBlur(grayImage, blurred, new Size(5, 5), 0)

            val threshed = new Mat(resized.size(), CV_8UC1)
            threshold(blurred, threshed, 60, 255, THRESH_BINARY)

            val contours = new UMatVector()
            findContours(threshed, contours, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE)
      */
      println(s"${contours.size()} contours found!")
    }
  }

  private def runCvTest(): Boolean = {
    detectShapes()
    false
  }
}
