package c.x.wzs.basic.concurrrency

import zio._
import c.x.wzs.basic.utils._
import org.scalafmt.config.Comments.Wrap.no

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

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = downloadFileWithRef()
}
