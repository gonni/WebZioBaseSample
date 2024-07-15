package c.x.wzs.httptwirl

import html.hellotwirl
import io.netty.handler.codec.http.{HttpHeaderNames, HttpHeaderValues}
import zio.*
import zio.http.*
import zio.http.endpoint.openapi.OpenAPI.SecurityScheme.Http

case class HelloTwirlController() {
  def routes: Routes[Any, Response] = Routes(
   Method.GET / "hella" -> handler {(req: Request) =>

     val msg = "Hell_" + java.util.Random().nextLong()
     Response(
       Status.Ok,
       Headers(Header.ContentType(MediaType.text.html).untyped),
       Body.fromString(hellotwirl.render(msg).toString)
     )
   }
  )
}


object HelloTwirlController {
  val live = ZLayer.derive[HelloTwirlController]
}
