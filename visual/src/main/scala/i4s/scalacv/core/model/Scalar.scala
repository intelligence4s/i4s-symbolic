package i4s.scalacv.core.model

object Scalar {
  def apply(s: org.bytedeco.opencv.opencv_core.Scalar): Scalar = new Scalar(s.get(0),s.get(1),s.get(2),s.get(3))

  import scala.language.implicitConversions
  implicit def s2s(s: org.bytedeco.opencv.opencv_core.Scalar): Scalar = apply(s)
}

case class Scalar(v0: Double, v1: Double, v2: Double, v3: Double) extends org.bytedeco.opencv.opencv_core.Scalar(v0,v1,v2,v3) with ScalarLike[Double] {
  override def construct(v0: Double, v1: Double, v2: Double, v3: Double): ScalarLike[Double] = Scalar(v0,v1,v2,v3)
}
