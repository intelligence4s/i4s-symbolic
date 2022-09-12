package i4s.scalacv.core.model

import i4s.scalacv.core.model.Math.NumberLike

object Rect2d {
  def apply(r: org.bytedeco.opencv.opencv_core.Rect2d): Rect2d = new Rect2d(r.x,r.y,r.width,r.height)
  def apply(ul: Point2d, lr: Point2d): Rect2d = Rect2d(ul.x,ul.y,math.abs(lr.x-ul.x),math.abs(lr.y-ul.y))

  import scala.language.implicitConversions
  implicit def r2r(r: org.bytedeco.opencv.opencv_core.Rect2d): Rect2d = apply(r)
  implicit def rl2r(rl: RectLike[Double]): Rect2d = rl.asInstanceOf[Rect2d]

}

case class Rect2d(override val x: Double, override val y: Double, override val width: Double, override val height: Double)
  extends org.bytedeco.opencv.opencv_core.Rect2d(x,y,width,height) with RectLike[Double]
{
  override def construct(x: Double, y: Double, width: Double, height: Double): RectLike[Double] = Rect2d(x,y,width,height)

  override def moveTo(p: PointLike[Double]): Rect2d = super.moveTo(p)
  override def scaleBy(sz: SizeLike[Double])(implicit ev: NumberLike[Double]): Rect2d = super.scaleBy(sz)
  override def shiftBy(sz: SizeLike[Double])(implicit ev: NumberLike[Double]): Rect2d = super.shiftBy(sz)

  override def tl(): Point2d = Point2d(super.tl)
  override def br(): Point2d = Point2d(super.br)

}
