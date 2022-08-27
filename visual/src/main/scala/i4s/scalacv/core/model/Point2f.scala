package i4s.scalacv.core.model

object Point2f {
  def apply(x: Float, y: Float): Point2f = new Point2f(x,y)

  def apply(p: org.bytedeco.opencv.opencv_core.Point2f): Point2f = new Point2f(p.x,p.y)

  import scala.language.implicitConversions
  implicit def p2p(p: org.bytedeco.opencv.opencv_core.Point2f): Point2f = apply(p)
}

class Point2f(override val x: Float, override val y: Float) extends org.bytedeco.opencv.opencv_core.Point2f(x,y) with PointLike[Float] {
  override def construct(x: Float, y: Float): PointLike[Float] = new Point2f(x,y)
}
