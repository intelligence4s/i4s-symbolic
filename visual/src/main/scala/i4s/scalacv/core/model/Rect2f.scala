package i4s.scalacv.core.model

import i4s.scalacv.core.model.Math.NumberLike

object Rect2f {
  def apply(r: org.bytedeco.opencv.opencv_core.Rect2f): Rect2f = new Rect2f(r.x,r.y,r.width,r.height)
  def apply(ul: Point2f, lr: Point2f): Rect2f = Rect2f(ul.x,ul.y,math.abs(lr.x-ul.x),math.abs(lr.y-ul.y))

  import scala.language.implicitConversions
  implicit def r2r(r: org.bytedeco.opencv.opencv_core.Rect2f): Rect2f = apply(r)
  implicit def rl2r(rl: RectLike[Float]): Rect2f = rl.asInstanceOf[Rect2f]
}

case class Rect2f(override val x: Float, override val y: Float, override val width: Float, override val height: Float)
  extends org.bytedeco.opencv.opencv_core.Rect2f(x,y,width,height) with RectLike[Float]
{
  override def construct(x: Float, y: Float, width: Float, height: Float): RectLike[Float] = Rect2f(x,y,width,height)

  override def moveTo(p: PointLike[Float]): Rect2f = super.moveTo(p)
  override def scaleBy(sz: SizeLike[Float])(implicit ev: NumberLike[Float]): Rect2f = super.scaleBy(sz)
  override def shiftBy(sz: SizeLike[Float])(implicit ev: NumberLike[Float]): Rect2f = super.shiftBy(sz)

  override def tl(): Point2f = Point2f(super.tl)
  override def br(): Point2f = Point2f(super.br)
}
