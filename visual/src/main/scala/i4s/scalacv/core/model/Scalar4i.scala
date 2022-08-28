package i4s.scalacv.core.model

object Scalar4i {
  def apply(s: org.bytedeco.opencv.opencv_core.Scalar4i): Scalar4i = new Scalar4i(s.get(0),s.get(1),s.get(2),s.get(3))

  import scala.language.implicitConversions
  implicit def s2s(s: org.bytedeco.opencv.opencv_core.Scalar4i): Scalar4i = apply(s)
}

case class Scalar4i(v0: Int, v1: Int, v2: Int, v3: Int) extends org.bytedeco.opencv.opencv_core.Scalar4i(v0,v1,v2,v3) with ScalarLike[Int] {
  override def construct(v0: Int, v1: Int, v2: Int, v3: Int): ScalarLike[Int] = Scalar4i(v0,v1,v2,v3)
}
