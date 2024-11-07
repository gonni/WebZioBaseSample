package c.x.wzs.basic.fiber

import zio._

object ForkMain extends ZIOAppDefault {
  
  def fib(n: Long): UIO[Long] = 
    ZIO.suspendSucceed {
      if(n <= 1) ZIO.succeed(n)
      else fib(n-1).zipWith(fib(n-2))(_ + _)
    }
  
  val fib100Fiber: UIO[Fiber[Nothing, Long]] = 
    for {
      fiber <- fib(100).fork
    } yield fiber

  val app = for {
    fib <- fib100Fiber
    res <- fib.join
    _ <- Console.printLine("Fib Result ->" + res)
  } yield () 

  val app2 = for {
    fiber <- ZIO.succeed("Hi!ZIO").fork
    message <- fiber.await
  } yield message

  val app3 = for {
    fiber <- ZIO.succeed("Hi!").forever.fork
    exit <- fiber.interrupt
  } yield exit

  val app4 = for {
    fiber1 <- ZIO.succeed("Hi!").fork
    fiber2 <- ZIO.succeed("Bye!").fork
    fiber   = fiber1.zip(fiber2)
    tuple  <- fiber.join
  } yield tuple

  val app5 = for {
    fiber1 <- ZIO.fail("Uh oh!").fork
    fiber2 <- ZIO.succeed("Hurray!").fork
    fiber   = fiber1.orElse(fiber2)
    message  <- fiber.join
  } yield message

  val app6 = for {
    winner <- ZIO.succeed("Hello").race(ZIO.succeed("Goodbye"))
  } yield winner
  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = app6.debug
}
