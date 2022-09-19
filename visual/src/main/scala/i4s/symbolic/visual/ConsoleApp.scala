package i4s.symbolic.visual

import i4s.symbolic.console.{CommandConfig, InteractiveConsole}
import i4s.symbolic.tf.TFTestCC

object ConsoleApp extends App {
  val configs: List[CommandConfig] = TFTestCC.configs() ::: VisualTestCC.configs()
  new InteractiveConsole(prompt = ">>> : ", configs).start()
}
