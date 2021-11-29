package i4s.symbolic.console

import scala.util.matching.Regex

case class CommandConfig(matcher: Regex, response: List[String] => Unit, help: String)

class InteractiveConsole(prompt: String, config: List[CommandConfig]) {
  val quitRe: Regex = "quit".r

  private val stdInSource = io.Source.stdin
  def start(): Unit = {
    print(prompt)

    try {
      for (line <- stdInSource.getLines) {
        (CommandConfig("quit".r, _ => stdInSource.close(), "quit") :: config)
          .flatMap(cc => cc.matcher.findFirstMatchIn(line).map(m => (m,cc.response)))
          .headOption
          .map { case (m, response) => response(m.subgroups) }
          .getOrElse(printHelp(config))

        print(prompt)
      }
    } catch {
      case _: java.io.IOException => println("exiting...")
    }
  }

  def interrupt(): Unit = stdInSource.close()

  def printHelp(config: List[CommandConfig]): Unit = {
    config.foreach(cc => println(cc.help))
  }

  def processSentence(sentence: String): Unit = {
    println(s"SENTENCE: $sentence")
  }
}
