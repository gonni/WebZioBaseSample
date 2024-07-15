package c.x.wzs.main

import c.x.wzs.httptwirl.HelloTwirlController
import zio.*
import zio.http.*

import java.net.URI


object Main extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val basicRoutes = Routes(
      Method.GET / "" -> handler(Response.redirect(URL.fromURI(URI.create("/hella")).get))
    )

    val httpApp = ZIO.service[HelloTwirlController].map { hella =>
      basicRoutes.toHttpApp ++ hella.routes.toHttpApp
    }

    val program =
      for{
        app <- httpApp
        _ <- Console.printLine("Start Server ..")
        _ <- Server.serve(app @@ Middleware.debug @@ Middleware.flashScopeHandling)
        _ <- Console.printLine("Server started .. !!")
      } yield()

    program.provide(
      HelloTwirlController.live,
      Server.default
    ).exitCode
  }
}
