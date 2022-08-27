package i4s.scalacv.core.model

import i4s.scalacv.core.model.Math.NumberLike

trait PointLike[T] {
  protected val x: T
  protected val y: T

  def construct(x: T, y: T): PointLike[T]

  def +(p: PointLike[T])(implicit ev: NumberLike[T]): PointLike[T] = construct(ev.plus(x,p.x),ev.plus(y,p.y))

  def -(p: PointLike[T])(implicit ev: NumberLike[T]): PointLike[T] = construct(ev.minus(x,p.x),ev.minus(y,p.y))

  def *(p: PointLike[T])(implicit ev: NumberLike[T]): PointLike[T] = construct(ev.multiply(x,p.x),ev.multiply(y,p.y))

  def /(p: PointLike[T])(implicit ev: NumberLike[T]): PointLike[T] = construct(ev.divide(x,p.x),ev.divide(y,p.y))

  def *(a: T)(implicit ev: NumberLike[T]): PointLike[T] = construct(ev.multiply(x,a),ev.multiply(y,a))

  def /(a: T)(implicit ev: NumberLike[T]): PointLike[T] = construct(ev.divide(x,a),ev.divide(y,a))

  def canEqual(that: Any): Boolean = that.isInstanceOf[PointLike[T]]

  override def equals(that: Any): Boolean = that match {
    case that: PointLike[T] => that.canEqual(this) && this.x == that.x && this.y == that.y
    case _ => false
  }

  override def hashCode(): Int = Int.hashCode() * x.hashCode() + y.hashCode()

  override def toString: String = s"($x,$y)"

}
