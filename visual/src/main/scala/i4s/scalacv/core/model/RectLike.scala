package i4s.scalacv.core.model

import i4s.scalacv.core.model.Math.NumberLike

trait RectLike[T] {
  protected val x: T
  protected val y: T
  protected val width: T
  protected val height: T

  def construct(x: T, y: T, width: T, height: T): RectLike[T]

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

  override def toString: String = s"($x,$y)"

}
