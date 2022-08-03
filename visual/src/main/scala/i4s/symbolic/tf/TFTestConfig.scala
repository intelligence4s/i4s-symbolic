package i4s.symbolic.tf

import org.tensorflow.Tensor
import org.tensorflow.ndarray.Shape
import org.tensorflow.types.TFloat32

object TFTestConfig {

  def makeTensor(): Unit = {
    val shape = Shape.of(1, 2)

    val t1 = Tensor.of(classOf[TFloat32], shape)
    println(t1)
    t1.close()
  }


}
