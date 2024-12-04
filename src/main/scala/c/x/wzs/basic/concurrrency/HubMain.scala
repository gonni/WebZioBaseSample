package c.x.wzs.basic.concurrrency

import zio._

object HubMain extends ZIOAppDefault {

  val hubApp = Hub.bounded[String](2).flatMap { hub =>
    ZIO.scoped {
      hub.subscribe.zip(hub.subscribe).flatMap { case (left, right) =>
        for {
          _ <- hub.publish("Hello from a hub!")
          _ <- left.take.flatMap(Console.printLine(_))
          _ <- right.take.flatMap(Console.printLine(_))
        } yield ()
      }
    }
  }
  
  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = hubApp
}
