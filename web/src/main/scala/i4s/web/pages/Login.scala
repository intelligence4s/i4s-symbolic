package i4s.web.pages

import i4s.web.components.Banner
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.core.facade.ReactElement
import slinky.web.html._

@react object Login {
  implicit def executionContext = scala.scalajs.concurrent.JSExecutionContext.Implicits.queue  

  case class Props()
  val component = FunctionalComponent[Props] { props =>
    val (email, setEmail) = useState[Option[String]](None)
    val (password, setPassword) = useState[Option[String]](None)

    def loginForm: ReactElement = {

      val inputClass = """mt-1 block w-80 px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400
      focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500
      disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none
      invalid:border-pink-500 invalid:text-pink-600
      focus:invalid:border-pink-500 focus:invalid:ring-pink-500"""

      form(
        className := "flex flex-col flex-grow justify-center items-center px-4 space-y-6",
        input(
          className := inputClass,
          placeholder := "email",
          name := "email",
          `type` := "text",
          onChange := (event => setEmail(Some(event.target.value)))
        ),
        input(
          className := inputClass,
          placeholder := "password",
          name := "password",
          `type` := "password",
          onChange := (event => setPassword(Some(event.target.value)))
        ),
        input(className := "px-4 py-1 text-sm text-white bg-blue-500 font-semibold rounded-md border border-blue-200 hover:bg-blue-600 hover:border-transparent hover:pointer-events-auto focus:outline-none focus:ring-2 focus:ring-blue-600 focus:ring-offset-2", `type` := "submit", value := "login")
      )
    }

    div(
      className := "h-screen flex flex-col",
      Banner(),
      loginForm,
    )
  }
}