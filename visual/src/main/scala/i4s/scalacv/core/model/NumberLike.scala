package i4s.scalacv.core.model

import scala.annotation.implicitNotFound

object Math {
  trait NumberLike[T] {
    def plus(x: T, y: T): T
    def minus(x: T, y: T): T
    def multiply(x: T, y: T): T
    def divide(x: T, y: T): T

    def power(x: T, y: T): T
    def root(x: T, Y: T): T
  }

  object NumberLike {

    @implicitNotFound("No member of type class NumberLike in scope for ${T}")
    implicit object NumberLikeInt extends NumberLike[Int] {
      def plus(x: Int, y: Int): Int = x + y
      def minus(x: Int, y: Int): Int = x - y
      def multiply(x: Int, y: Int): Int = x * y
      def divide(x: Int, y: Int): Int = x / y
      def power(x: Int, y: Int): Int = scala.math.pow(x,y).toInt
      def root(x: Int, y: Int): Int = scala.math.pow(x,1/ y.toDouble).toInt
    }

    implicit object NumberLikeDouble extends NumberLike[Double] {
      def plus(x: Double, y: Double): Double = x + y
      def minus(x: Double, y: Double): Double = x - y
      def multiply(x: Double, y: Double): Double = x * y
      def divide(x: Double, y: Double): Double = x / y
      def power(x: Double, y: Double): Double = scala.math.pow(x,y).toInt
      def root(x: Double, y: Double): Double = scala.math.pow(x,1/ y.toDouble).toInt
    }

    implicit object NumberLikeFloat extends NumberLike[Float] {
      def plus(x: Float, y: Float): Float = x + y
      def minus(x: Float, y: Float): Float = x - y
      def multiply(x: Float, y: Float): Float = x * y
      def divide(x: Float, y: Float): Float = x / y
      def power(x: Float, y: Float): Float = scala.math.pow(x,y).toInt
      def root(x: Float, y: Float): Float = scala.math.pow(x,1/ y.toDouble).toInt
    }
  }
}
