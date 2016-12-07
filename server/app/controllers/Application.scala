package controllers


import java.util.Date

import play.api.mvc._
import shared.{Api, Message}
import upickle.Js
import autowire.AutowireServer
import play.api.libs.concurrent.Execution.Implicits._

class Application extends Controller with Api {
  def index = Action {
    Ok(views.html.index(""))
  }

  def api(path: String) = Action.async { request =>
    val body = request.body.asText.getOrElse("")
    AutowireServer.route[Api](this)(
      autowire.Core.Request(
        path.split("/"),
        upickle.json.read(body).asInstanceOf[Js.Obj].value.toMap
      )
    ).map(upickle.json.write(_)).map{
      body =>
        Ok(body)
    }
  }

  def greet(name : String) =  Message(s"Greetings from play server, $name! Time is now ${new Date}")

}
