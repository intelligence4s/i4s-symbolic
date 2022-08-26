package i4s.scalacv.image

import i4s.scalacv.image.constants.ImageReadFlags
import i4s.scalacv.image.constants.ImageReadFlags.ImageReadFlags
import org.bytedeco.opencv.opencv_core.{Mat, MatVector}
import org.bytedeco.opencv.global.opencv_imgcodecs._

import java.io.File

object Codecs {
  import Image._

  def readImage(file: File): Option[Image] = readImage(file,ImageReadFlags.AnyColor)
  def readImage(file: File, readFlag: ImageReadFlags): Option[Image] = readImage(file,Set(readFlag))
  def readImage(file: File, readFlags: Set[ImageReadFlags]): Option[Image] = {
    val combined = readFlags.foldLeft(0)(_ | _.id)
    val mat = imread(file.getAbsolutePath,combined)
    if (mat.empty()) None else Some(mat)
  }

  def readImages(file: File): List[Image] = readImages(file,ImageReadFlags.AnyColor)
  def readImages(file: File, readFlag: ImageReadFlags): List[Image] = readImages(file,Set(readFlag))
  def readImages(file: File, readFlags: Set[ImageReadFlags]): List[Image] = {
    val combined = readFlags.foldLeft(0)(_ | _.id)
    val matVector = new MatVector()
    val result = imreadmulti(file.getAbsolutePath,matVector,combined)
    if (result) matVectorToList(matVector)
    else Nil
  }

  def readImages(file: File, start: Int, count: Int): List[Image] = readImages(file,start,count,ImageReadFlags.AnyColor)
  def readImages(file: File, start: Int, count: Int, readFlag: ImageReadFlags): List[Image] = readImages(file,start,count,Set(readFlag))
  def readImages(file: File, start: Int, count: Int, readFlags: Set[ImageReadFlags]): List[Image] = {
    val combined = readFlags.foldLeft(0)(_ | _.id)
    val matVector = new MatVector()
    val result = imreadmulti(file.getAbsolutePath,matVector,start,count,combined)
    if (result) matVectorToList(matVector)
    else Nil
  }


  def imageCount(file: File): Option[Long] = imageCount(file, ImageReadFlags.AnyColor)
  def imageCount(file: File, readFlag: ImageReadFlags): Option[Long] = imageCount(file,Set(readFlag))
  def imageCount(file: File, readFlags: Set[ImageReadFlags]): Option[Long] = {
    val combined = readFlags.foldLeft(0)(_ | _.id)
    val result = imcount(file.getAbsolutePath, combined)
    if (result < 0) None else Some(result)
  }

  private def matVectorToList(vector: MatVector): List[Image] = vector.get().toList.map(m => new Image(m))
}

