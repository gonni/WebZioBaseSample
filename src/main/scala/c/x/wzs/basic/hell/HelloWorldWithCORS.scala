package c.x.wzs.basic.hell

import zio._

import zio.http.Header.{AccessControlAllowOrigin, Origin}
import zio.http.Middleware.{CorsConfig, cors}
import zio.http._
import zio.http.codec.PathCodec
import zio.http.template._

object HelloWorldWithCORS extends ZIOAppDefault {
    
  val config: CorsConfig =
    CorsConfig(
      allowedOrigin = {
        case origin if origin == Origin.parse("http://localhost:3000").toOption.get =>
          Some(AccessControlAllowOrigin.Specific(origin))
        case _                                                                      => None
      },
    )

  val backend: Routes[Any, Response] =
    Routes(
      Method.GET / "json" -> 
      handler{
        for {
            _ <- ZIO.logInfo("detected json request ..")
            res <- ZIO.succeed(Response.json("""{"message": "Hello World!"}"""))
        } yield res
      },
       ) @@ cors(config)

  val frontend: Routes[Any, Response] =
    Routes(
      Method.GET / PathCodec.empty -> handler(
        Response.html(
          html(
            p("Message: ", output()),
            script("""
                     |// This runs on http://localhost:3000
                     |fetch("http://localhost:8080/json")
                     |  .then((res) => res.json())
                     |  .then((res) => document.querySelector("output").textContent = res.message);
                     |""".stripMargin),
          ),
        ),
      ),
    )

  val frontEndServer = Server.serve(frontend).provide(Server.defaultWithPort(3000))
  val backendServer  = Server.serve(backend).provide(Server.defaultWithPort(8080))

  val run = frontEndServer.zipPar(backendServer)
}