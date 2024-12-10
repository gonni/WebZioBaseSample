package c.x.wzs.basic.concurrrency

import zio._
import c.x.wzs.basic.utils._
import org.scalafmt.config.Comments.Wrap.no
import c.x.wzs.basic.fiber.BlockingEffects.program
import java.util.concurrent.TimeUnit

object PromisesMain extends ZIOAppDefault {

  val aPromise: UIO[Promise[Throwable, Int]] = Promise.make[Throwable, Int]

  val fileParts = List("I ", "love ", "Scala ", "with FP and ", "ZIOI! <EOF>")

  def downloadFileWithRef(): UIO[Unit] = {
    def downloadFile(contentRef: Ref[String]): UIO[Unit] = 
      ZIO.collectAllDiscard(
        fileParts.map { part =>
          ZIO.succeed(s"got '$part'").debugThread *> ZIO.sleep(1.second) *> contentRef.update(_ + part)
        }
      )

    def notifyFileComplete(contentRef: Ref[String]): UIO[Unit] = for {
      file <- contentRef.get
      _ <- if(file.endsWith("<EOF>")) ZIO.succeed("File download complete").debugThread 
            else ZIO.succeed("downloading ...").debugThread *> ZIO.sleep(1.second) *> notifyFileComplete(contentRef)
    } yield ()

    for {
      contentRef <- Ref.make("")  
      _ <- downloadFile(contentRef) zipPar notifyFileComplete(contentRef)
    } yield ()
  }

  def downloadFileWithRefPromise(): Task[Unit] = {
    def downloadFile(contentRef: Ref[String], promise: Promise[Throwable, String]): UIO[Unit] = 
      ZIO.collectAllDiscard(
        fileParts.map { part =>
          for {
            _ <- ZIO.succeed(s"got '$part'").debugThread 
            _ <- ZIO.sleep(1.second)
            file <- contentRef.updateAndGet(_ + part)
            _ <- if(file.endsWith("<EOF>")) promise.succeed(file) else ZIO.unit
          } yield ()
        }
      )

    def notifyFileComplete(contentRef: Ref[String], promise: Promise[Throwable, String]) = for {
      _ <- ZIO.succeed("downloading ...").debugThread
      file <- promise.await
      _ <- ZIO.succeed(s"File download complete: $file").debugThread
    } yield ()

    for {
      contentRef <- Ref.make("")  
      promise <- Promise.make[Throwable, String]
      _ <- downloadFile(contentRef, promise) zipPar notifyFileComplete(contentRef, promise) //.orDie
    } yield ()
  }
  
  // // -----
  // def boiler(degree: Ref[Int]): UIO[Unit] = 
  //   for {
  //     _ <- ZIO.debug(s"Boiling egg in $degree.get").debugThread
  //     dg <- degree.updateAndGet(_ + 1)
  //     _ <- ZIO.sleep(1.second)
  //   } yield boiler(dg)

  // def boilWatcher(degree: Ref[Int], promise: Promise[Nothing, Int]): UIO[Unit] = for {
  //   _ <- ZIO.debug(s"Waiting boiling egg at ${degree.get} degree")
  //   _ <- promise.await
  //   _ <- ZIO.succeed("Ring a bell").debugThread
  // } yield ()

  def eggBoiler() = {
    def eggReady(signal: Promise[Nothing, Unit]) = for {
      _ <- ZIO.succeed("Egg boiling on same other fiber, waiting ...").debugThread
      _ <- signal.await
      _ <- ZIO.succeed("Egg is ready").debugThread
    } yield ()

    def tickingClock(ticks: Ref[Int], signal: Promise[Nothing, Unit]): UIO[Unit] = for {
      _ <- ZIO.sleep(1.second)
      count <- ticks.updateAndGet(_ + 1)
      _ <- ZIO.succeed(count).debugThread
      _ <- if (count >= 10) signal.succeed(()) else tickingClock(ticks, signal)
    } yield ()

    for {
      ticks <- Ref.make(0)
      signal <- Promise.make[Nothing, Unit]
      _ <- eggReady(signal) zipPar tickingClock(ticks, signal)
    } yield ()
  }

  val race: IO[String, Int] = for {
    p     <- Promise.make[String, Int]
    _     <- (ZIO.sleep(Duration(2, TimeUnit.SECONDS)) *> p.succeed(1)).fork
    _     <- p.complete(ZIO.succeed(2)).fork
    _     <- p.completeWith(ZIO.succeed(3)).fork
    _     <- p.done(Exit.succeed(4)).fork
    _     <- p.fail("5")
    _     <- p.failCause(Cause.die(new Error("6")))
    _     <- p.die(new Error("7"))
    _     <- p.interrupt.fork
    value <- p.await
  } yield value

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = race.debug
}
