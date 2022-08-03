package i4s.symbolic.visual

import i4s.symbolic.console.{CommandConfig, InteractiveConsole}
import i4s.symbolic.tf.TFTestConfig

object ConsoleApp extends App {

  val config: List[CommandConfig] = {
    CommandConfig("^(test)$".r, values => runTensorTest(), "test") ::
      Nil
  }

  def runTensorTest(): Boolean = {
    TFTestConfig.makeTensor()
    false
  }

  new InteractiveConsole(prompt = ">>> : ", config).start()

}
