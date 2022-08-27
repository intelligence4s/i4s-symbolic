package i4s.scalacv.core.model

object Point2d {
  def apply(x: Double, y: Double): Point2d = new Point2d(x,y)

  def apply(p: org.bytedeco.opencv.opencv_core.Point2d): Point2d = new Point2d(p.x,p.y)

  import scala.language.implicitConversions
  implicit def p2p(p: org.bytedeco.opencv.opencv_core.Point2d): Point2d = apply(p)
}

class Point2d(override val x: Double, override val y: Double) extends org.bytedeco.opencv.opencv_core.Point2d(x,y) with PointLike[Double] {
  override def construct(x: Double, y: Double): PointLike[Double] = new Point2d(x,y)

}
