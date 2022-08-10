package i4s.symbolic.visual

import i4s.scalacv.image.Codecs
import i4s.symbolic.console.{CommandConfig, HasCommandConfigs}
import org.bytedeco.javacv._
import org.bytedeco.javacpp._
import org.bytedeco.javacpp.indexer._
import org.bytedeco.opencv.opencv_core._
import org.bytedeco.opencv.opencv_imgproc._
import org.bytedeco.opencv.opencv_calib3d._
import org.bytedeco.opencv.opencv_objdetect._
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_calib3d._
import org.bytedeco.opencv.global.opencv_objdetect._

import java.io.File

object VisualTestCC extends HasCommandConfigs {

  override def configs(): List[CommandConfig] = List(
    CommandConfig("^(testcv)$".r, values => runCvTest(), "test")
  )

  private def detectShapes(): Unit = {
    val testFile = new File(getClass.getClassLoader.getResource("shapes_and_colors.jpeg").getFile)

    for {
      image <- Codecs.readImage(testFile)
    } yield {
      val ratio = 300f / image.size().width()
      val resized = new Mat()
      resize(image, resized, new Size(), ratio, ratio, INTER_AREA)

      val grayImage: Mat = new Mat(resized.size, CV_8UC1)
      cvtColor(image, grayImage, CV_BGR2GRAY)

      val blurred = new Mat(resized.size(), CV_8UC1)
      GaussianBlur(grayImage, blurred, new Size(5, 5), 0)

      val threshed = new Mat(resized.size(), CV_8UC1)
      threshold(blurred, threshed, 60, 255, THRESH_BINARY)

      val contours = new UMatVector()
      findContours(threshed, contours, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE)

      println(s"${contours.size()} contours found!")
    }
  }

  private def runCvTest(): Boolean = {
    detectShapes()
    false
  }
}
