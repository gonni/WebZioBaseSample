package c.x.wzs.client

import zio.*
import zio.http.*
import zio.json.*
//import zio.schema._
//import zio.schema.DeriveSchema._

class StockServiceLocal() {

  def getKosdaqIndexPred(): ZIO[Client & Scope, Either[String, Throwable], KosdaqIndex] = {
    val url = URL.decode("http://127.0.0.1:8088/predKosdaq").toOption.get
    import c.x.wzs.client.KosdaqIndex._

    (for {
      client <- ZIO.service[Client]
      res <- client.url(url).get("")
      data <- res.body.asString
    } yield data.fromJson[KosdaqIndex]).right
  }

}

object StockServiceLocal {
  def create(): StockServiceLocal = new StockServiceLocal

  val live: ZLayer[Any, Throwable, StockServiceLocal] =
    ZLayer.fromFunction(create _)

//  def layer: ZLayer[Any, Nothing, StockServiceLocal] =
//    ZLayer.fromFunction(new StockServiceLocal _)
}