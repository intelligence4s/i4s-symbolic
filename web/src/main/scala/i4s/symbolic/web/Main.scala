package i4s.symbolic.web

import i4s.symbolic.web.pages.{Login, NotFound}
import org.scalajs.dom
import slinky.core.BuildingComponent
import slinky.history.History
import slinky.hot
import slinky.reactrouter._
import slinky.web.ReactDOM

import scala.scalajs.js.annotation.{JSExportTopLevel, JSImport}
import scala.scalajs.{LinkingInfo, js}
import i4s.symbolic.web.pages._

@JSImport("resources/App.css", JSImport.Default)
@js.native
object AppCSS extends js.Object

object Main {
  val css = AppCSS

  @JSExportTopLevel("main")
  def main(args: Array[String]): Unit = {
    if (LinkingInfo.developmentMode) {
      hot.initialize()
    }

    val history = History.createBrowserHistory()

    val routes: BuildingComponent[_, js.Object] = Switch(
      Route.apply("/", Login.component, exact = true),
      Route.apply("/login", Login.component),
      Route.apply("/sales", SalesChart.component),
      Route.apply("/grammar", Grammar.component),
      Route.apply("*", NotFound.component)
    )

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    ReactDOM.render(
      Router(history = history)(routes),
      container
    )
  }
}
