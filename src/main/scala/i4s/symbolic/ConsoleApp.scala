package i4s.symbolic

import i4s.symbolic.console.InteractiveConsole

object ConsoleApp extends App {
  new InteractiveConsole(prompt = ">>> : ").start()
}
