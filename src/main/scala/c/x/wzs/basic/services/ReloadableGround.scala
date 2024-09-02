package c.x.wzs.basic.services

import zio.*

import java.io.IOException

trait Counter {
  def increment: UIO[Unit]
  def get: UIO[Int]
}

object Counter {

  val increment: ZIO[Counter, Nothing, Unit] =
    ZIO.serviceWithZIO(_.increment)

  val get: ZIO[Counter, Nothing, Int] =
    ZIO.serviceWithZIO(_.get)

  val live: ZLayer[Any, Nothing, Counter] =
    ZLayer.scoped {
      for {
        id <- Ref.make(0)
        ref <- Ref.make(0)
        service = CounterLive(id, ref)
        _ <- service.acquire
        _ <- ZIO.addFinalizer(service.release)
      } yield service
    }

  val reloadable: ZLayer[Any, Nothing, Reloadable[Counter]] = live.reloadableManual

  final case class CounterLive(id: Ref[Int], ref: Ref[Int]) extends Counter {
    def acquire: UIO[Unit] = Random.nextInt.flatMap(n => id.set(n) *> ZIO.debug(s"Acquire counter $n"))
    override def increment: UIO[Unit] = ref.update(_ + 1)
    override def get: UIO[RuntimeFlags] = ref.get
    def release: UIO[Unit] = id.get.flatMap(n => ZIO.debug(s"Release counter $n"))
  }
}


object ReloadableGround extends ZIOAppDefault {

  val app: ZIO[Counter, IOException, Unit] = for {
    _ <- Counter.increment
    _ <- Counter.increment
    _ <- Counter.increment
    n <- Counter.get
    _ <- Console.printLine(s"Counter value: $n")
  } yield ()

  val app2: ZIO[Reloadable[Counter], IOException, Unit] = for {
    reloadable <- ZIO.service[Reloadable[Counter]]
    counter <- reloadable.get
    _ <- counter.increment
    _ <- counter.increment
    _ <- counter.increment
    n <- counter.get
    _ <- Console.printLine(s"Counter value: $n")
    _ <- reloadable.reloadFork
    _ <- ZIO.sleep(1.second)
    counter <- reloadable.get
    _ <- counter.increment
    _ <- counter.increment
//    _ <- counter.increment
    n <- counter.get
    _ <- Console.printLine(s"2th Counter value: $n")

  } yield ()

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    app2.provide(
      Counter.reloadable
    )
//    app.provide(
//      Counter.live
//    )
}
