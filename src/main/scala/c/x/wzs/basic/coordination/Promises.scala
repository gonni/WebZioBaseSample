package c.x.wzs.basic.coordination

import zio._
import c.x.wzs.basic.utils._

object Promises extends ZIOAppDefault {

  val aPromise: UIO[Promise[Throwable, Int]] = Promise.make[Throwable, Int]
  
  // await - block the fiber until the promise has a value
  val reader = aPromise.flatMap{ promise =>
    promise.await
  }

  val writer = aPromise.flatMap { promise =>
    promise.succeed(42)
  }

  def demoPromise(): UIO[Unit] = {
    // producer - consumer problem
    def consumer(promise : Promise[Throwable, Int]) = for {
      _ <- ZIO.succeed("[consumer] waiting for result ...").debugThread
      mol <- promise.await
      _ <- ZIO.succeed(s"[consummer] I got the result: $mol").debugThread
    } yield()

    def producer(promise: Promise[Throwable, Int]) = for {
      _ <- ZIO.succeed("[producer] crunching numbers ...").debugThread
      _ <- ZIO.sleep(3.seconds)
      _ <- ZIO.succeed("[producer] complete.").debugThread
      mol <- ZIO.succeed(42)
      _ <- promise.succeed(mol)
    } yield()

    for {
      promise <- Promise.make[Throwable, Int]
      _ <- (consumer(promise) zipPar producer(promise)).orDie
    } yield()
   }

   /*
    - puraly functional block on a fiber until you get a signal from another fiber
    - waiting on a value which may not yet be available, without thread starvation
    - inter-fiber communication 
     */
   val fileParts = List("I ", "love S", "cala ", "with pure FP an", "d ZIO! <EOF>")
   def downloadFileWithRef(): UIO[Unit] = {
    def downloadFile(contentRef: Ref[String]): UIO[Unit] = 
      ZIO.collectAllDiscard(
        fileParts.map { part =>
          ZIO.succeed(s"got '$part'").debugThread *> ZIO.sleep(1.seconds) *> contentRef.update(_ + part)
        }
      )

    def notifyFileComplete(contentRef: Ref[String]): UIO[Unit] = for {
      file <- contentRef.get
      _ <- if(file.endsWith("<EOF>")) ZIO.succeed("File download completed..").debugThread
        else ZIO.succeed("downloading ...").debugThread *> ZIO.sleep(500.millis) *> notifyFileComplete(contentRef)
    } yield()

    for {
      contentRef <- Ref.make("")
      _ <- downloadFile(contentRef) zipPar notifyFileComplete(contentRef)
    } yield()
   }

   // Final
   def downloadFileWithRefPromise(): UIO[Unit] = {
    def downloadFile(contentRef: Ref[String], promise: Promise[Throwable, String]): UIO[Unit] = 
      ZIO.collectAllDiscard(
        fileParts.map { part =>
          for {
            _ <- ZIO.succeed(s"get '$part'").debugThread
            _ <- ZIO.sleep(1.second)
            file <- contentRef.updateAndGet(_ + part)
            _ <- if(file.endsWith("<EOF>")) promise.succeed(file) else ZIO.unit
          } yield()
        }
      )

    def notifyFileComplete(contentRef: Ref[String], promise: Promise[Throwable, String]): UIO[Unit] = for {
      _ <- ZIO.succeed("downloading ..").debugThread
      file <- promise.await.orDie
      _ <- ZIO.succeed(s"file download complete: $file").debugThread
     } yield()

    for {
      contentRef <- Ref.make("")
      promise <- Promise.make[Throwable, String]
      _ <- downloadFile(contentRef, promise) zipPar notifyFileComplete(contentRef, promise)
    } yield()
   }

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = downloadFileWithRefPromise()
}
