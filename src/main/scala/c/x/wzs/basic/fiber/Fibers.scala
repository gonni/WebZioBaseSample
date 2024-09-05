package c.x.wzs.basic.fiber

import zio._
import c.x.wzs.basic.utils._

object Fibers extends ZIOAppDefault {

  val meaningOfLife = ZIO.succeed(42)
  val favLang = ZIO.succeed("Scala")

  def createFiber: Fiber[Throwable, String] = ???

  def sameThreadIO = for {
    mol <- meaningOfLife.debugThread
    lang <- favLang.debugThread
  } yield (mol, lang)

  val differentThreadIO = for {
    _ <- meaningOfLife.debugThread.fork
    _ <- favLang.debugThread.fork
  } yield ()

  val meaningOfLifeFiber: ZIO[Any, Nothing, Fiber[Throwable, Int]] = meaningOfLife.fork

  // join a fiber
  def runOnAnotherThread[R,E,A](zio: ZIO[R,E,A]) = for {
    fib <- zio.fork
    result <- fib.join    // block
  } yield result

  def runOnAnotherThread_v2[R,E,A](zio: ZIO[R,E,A]) = for {
    fib <- zio.fork
    result <- fib.await
  } yield result match {
    case Exit.Success(value) => s"succeed with $value"
    case Exit.Failure(cause) => s"failed with $cause"
  }

  // poll - peak at the result of the fiber RIGHT NOW, without blocking
  val peekFiber = for {
    fib <- ZIO.attempt{
      Thread.sleep(1000)
      42
    }.fork
    result <- fib.poll
  } yield result

  // compose fibers
  val zippedFibers = for {
    fib1 <- ZIO.succeed("Result from fiber 1").debugThread.fork
    fib2 <- ZIO.succeed("Result from fiber 2").debugThread.fork
    fiber = fib1.zip(fib2)
    tuple <- fiber.join
  } yield tuple

  val chainedFibers = for {
    fiber1 <- ZIO.fail("not good!").debugThread.fork
    fiber2 <- ZIO.succeed("R JVM").debugThread.fork
    fiber = fiber1.orElse(fiber2)
    message <- fiber.join
  } yield message


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = chainedFibers.debugThread
}
