package c.x.wzs.client

import zio._
import zio.http._
import zio.json.*

object ConnWithPredKos extends ZIOAppDefault {
  val url = URL.decode("http://127.0.0.1:8088/predKosdaq").toOption.get

//  val program = for {
//    _ <- Console.printLine("Start Program ..")
//    client <- ZIO.service[Client]
//    res <- client.url(url).get("")
//    data <- res.body.asString
//    _ <- Console.printLine("recv -> " + data.fromJson[KosdaqIndex])
//  } yield ()

  val program = for {
    _ <- Console.printLine("Start Program2 ..")
    stsl <- ZIO.service[StockServiceLocal]
    res <- stsl.getKosdaqIndexPred()
    _ <- Console.printLine("Result => " + res)
  } yield()

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    program
      .provide(Client.default, Scope.default, StockServiceLocal.live)
}
