package c.x.wzs.main

import c.x.wzs.dbio.{PersistController, PersistentUserRepo}
import c.x.wzs.httptwirl.HelloTwirlController
import c.x.wzs.restful.*
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio.*
import zio.http.*

import java.net.URI


object Main extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
//    val basicRoutes = Routes(
//      Method.GET / "" -> handler(Response.redirect(URL.fromURI(URI.create("/hella")).get))
//    )
//    val httpApp = ZIO.service[HelloTwirlController].map { hella =>
//      basicRoutes.toHttpApp ++ hella.routes.toHttpApp
//    }
//
//    val program =
//      for{
//        app <- httpApp
//        _ <- Console.printLine("Start Server ..")
//        _ <- Server.serve(app @@ Middleware.debug @@ Middleware.flashScopeHandling)
//        _ <- Console.printLine("Server started .. !!")
//      } yield()
//
//    program.provide(
//      HelloTwirlController.live,
//      Server.defaultWithPort(8080),
//      InmemoryUserRepo.layer
//    ).exitCode
    // -----
//    Server
//      .serve(
//        HelloTwirlController().routes.toHttpApp ++ UserRoutes().toHttpApp
//      )
//      .provide(
//        Server.defaultWithPort(8080),
//        InmemoryUserRepo.layer,
//        PersistController.live,
//        PersistentUserRepo.live,
//        Quill.Mysql.fromNamingStrategy(SnakeCase),
//        Quill.DataSource.fromPrefix("ZioMysqlAppConfig")
//      )

    // ----- static route -----
    def staticFileHandler(path: Path): Handler[Any, Throwable, Request, Response] =
      for {
        file <- Handler.getResourceAsFile("static/" + path.encode)
        http <-
          if (file.isFile)
            Handler.fromFile(file)
          else
            Handler.notFound
      } yield http

    val staticRoutes =
      Routes(
        Method.GET / "static" / trailing ->
          Handler.fromFunctionHandler[(Path, Request)] { case (path: Path, _: Request) =>
            staticFileHandler(path).contramap[(Path, Request)](_._2)
          },
      ).sandbox @@ HandlerAspect.requestLogging()
    // ----- /static route -----

    val happs = ZIO.serviceWith[PersistController] {
      pc =>
        pc.routes.toHttpApp ++ HelloTwirlController().routes.toHttpApp ++ UserRoutes().toHttpApp ++ staticRoutes.toHttpApp
    }

    val program = for {
      app <- happs
      _ <- Server.serve(app @@ Middleware.debug)
    } yield ()

    program.provide(
      Server.defaultWithPort(8080),
      InmemoryUserRepo.layer,
      PersistController.live,
      PersistentUserRepo.live,
      Quill.Mysql.fromNamingStrategy(SnakeCase),
      Quill.DataSource.fromPrefix("ZioMysqlAppConfig")
    ).exitCode
  }
}
