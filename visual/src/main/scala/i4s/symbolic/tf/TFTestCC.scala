package i4s.symbolic.tf

import i4s.symbolic.console.{CommandConfig, HasCommandConfigs}
import org.tensorflow.Tensor
import org.tensorflow.ndarray.Shape
import org.tensorflow.types.TFloat32

object TFTestCC extends HasCommandConfigs {
  override def configs(): List[CommandConfig] = List(CommandConfig("^(testtf)$".r, values => runTensorTest(), "test"))

  private def makeTensor(): Unit = {
    val shape = Shape.of(1, 2)

    val t1 = Tensor.of(classOf[TFloat32], shape)
    println(t1)
    t1.close()
  }

  private def runTensorTest(): Boolean = {
    TFTestCC.makeTensor()
    false
  }

}
