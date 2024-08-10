package c.x.wzs.mw

import zio.http._
import zio._

object SampleMiddlewares {
  val correlationId =
    Middleware.logAnnotate { req =>
      val correlationId =
        req.headers.get("X-Correlation-ID").getOrElse(
          Unsafe.unsafe { implicit unsafe =>
            Runtime.default.unsafe.run(Random.nextUUID.map(_.toString)).getOrThrow()
          }
        )

      Set(LogAnnotation("correlation-id", correlationId))
    }

  val whitelistMiddleware: HandlerAspect[Any, Unit] =
    HandlerAspect.interceptIncomingHandler {
      val whitelist = Set("127.0.0.1", "0.0.0.0")
      Handler.fromFunctionZIO[Request] { request =>
        request.headers.get("X-Real-IP") match {
          case Some(host) if whitelist.contains(host) =>
            ZIO.succeed((request, ()))
          case _ =>
            ZIO.fail(Response.forbidden("Your IP is banned from accessing the server."))
        }
      }
    }

  val addCustomHeader: HandlerAspect[Any, Unit] =
    HandlerAspect.interceptOutgoingHandler(
      Handler.fromFunction[Response](_.addHeader("X-Custom-Header", "Hello from Custom Middleware!")),
    )
}
