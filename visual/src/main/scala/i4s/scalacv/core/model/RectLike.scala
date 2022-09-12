package i4s.scalacv.core.model

import i4s.scalacv.core.model.Math.NumberLike

trait RectLike[T] {
  val x: T
  val y: T
  val width: T
  val height: T

  def construct(x: T, y: T, width: T, height: T): RectLike[T]

  def moveTo(p: PointLike[T]): RectLike[T] = construct(p.x,p.y,width,height)
  def shiftBy(sz: SizeLike[T])(implicit ev: NumberLike[T]): RectLike[T] = construct(ev.plus(x,sz.width), ev.plus(y,sz.height), width, height)
  def scaleBy(sz: SizeLike[T])(implicit ev: NumberLike[T]): RectLike[T] = construct(x,y,ev.multiply(width,sz.width),ev.multiply(height,sz.height))

  def canEqual(that: Any): Boolean = that.isInstanceOf[RectLike[T]]

  override def equals(that: Any): Boolean = that match {
    case that: RectLike[T] =>
      that.canEqual(this) &&
        this.x == that.x &&
        this.y == that.y &&
        this.width == that.width &&
        this.height == that.height
    case _ => false
  }

  override def hashCode(): Int = 1 << 32 * x.hashCode() + y.hashCode()

  override def toString: String = s"($x,$y,$width,$height)"

}
