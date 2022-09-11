package i4s.scalacv.core.model

object Rect {
  def apply(r: org.bytedeco.opencv.opencv_core.Rect): Rect = Rect(r.x,r.y,r.width,r.height)
  def apply(ul: Point, lr: Point): Rect = Rect(ul.x,ul.y,math.abs(lr.x-ul.x),math.abs(lr.y-ul.y))

  import scala.language.implicitConversions
  implicit def r2r(r: org.bytedeco.opencv.opencv_core.Rect): Rect = apply(r)
}

case class Rect(override val x: Int, override val y: Int, override val width: Int, override val height: Int) extends org.bytedeco.opencv.opencv_core.Rect(x,y,width,height) with RectLike[Int] {
  override def construct(x: Int, y: Int, width: Int, height: Int): RectLike[Int] = Rect(x,y,width,height)
}
