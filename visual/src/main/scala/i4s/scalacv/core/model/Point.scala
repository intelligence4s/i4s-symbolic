package i4s.scalacv.core.model

object Point {
  def apply(p: org.bytedeco.opencv.opencv_core.Point): Point = new Point(p.x,p.y)

  import scala.language.implicitConversions
  implicit def p2p(p: org.bytedeco.opencv.opencv_core.Point): Point = apply(p)
}

case class Point(override val x: Int, override val y: Int) extends org.bytedeco.opencv.opencv_core.Point(x,y) with PointLike[Int] {
  override def construct(x: Int, y: Int): PointLike[Int] = new Point(x,y)
}
