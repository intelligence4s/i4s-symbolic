package i4s.symbolic

import i4s.symbolic.console.{CommandConfig, InteractiveConsole}
import i4s.symbolic.language.LanguageProcessor

object ConsoleApp extends App {

  val processor = new LanguageProcessor

  val config: List[CommandConfig] = {
    CommandConfig("^(.*$)".r, values => processor.process(values.head), "<sentence>") ::
    Nil
  }

  new InteractiveConsole(prompt = ">>> : ", config).start()
}
