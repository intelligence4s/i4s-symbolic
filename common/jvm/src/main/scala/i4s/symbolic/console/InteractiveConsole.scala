package i4s.symbolic.console

import scala.util.matching.Regex
import scala.util.control.Breaks._

case class CommandConfig(matcher: Regex, response: List[String] => Boolean, help: String)

class InteractiveConsole(prompt: String, config: List[CommandConfig]) {
  val quitRe: Regex = "^quit$".r

  val handlesQuit = CommandConfig(quitRe, _ => interrupt, "quit") :: config

  private val stdInSource = io.Source.stdin
  def start(): Unit = {
    print(prompt)

    breakable {
      for (line <- stdInSource.getLines) {
        val shouldExit = handlesQuit
          .flatMap(cc => cc.matcher.findFirstMatchIn(line).map(m => (m,cc.response)))
          .headOption
          .map { case (m, response) => response(m.subgroups) }
          .getOrElse(printHelp(config))

        if (shouldExit) break()
        print(prompt)
      }
    }
  }

  def interrupt: Boolean = {
    stdInSource.close()
    true
  }

  def printHelp(config: List[CommandConfig]): Boolean = {
    config.foreach(cc => println(cc.help))
    false
  }
}
