package i4s.symbolic.nlp

import i4s.symbolic.console.{CommandConfig, InteractiveConsole}

object ConsoleApp extends App {

  val processor = new LanguageProcessor

  val config: List[CommandConfig] = {
    CommandConfig("^(.*$)".r, values => processStatement(values.head), "<sentence>") ::
      Nil
  }

  def processStatement(statement: String): Boolean = {
    processor.process(statement)
    false
  }

  new InteractiveConsole(prompt = ">>> : ", config).start()
}
