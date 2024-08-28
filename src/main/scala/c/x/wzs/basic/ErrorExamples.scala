package c.x.wzs.basic
import zio.*

import java.io.IOException
import java.net.NoRouteToHostException

object ErrorExamples extends ZIOAppDefault {
  // sandbox
  val failedInt: ZIO[Any, String, Int] = ZIO.fail("I failed!")
  val failureCauseExcetpion: ZIO[Any, Cause[String], Int] = failedInt.sandbox
  val failureCauseHidden: ZIO[Any, String, Int] =  failureCauseExcetpion.unsandbox
  // fold with cause
  val foldedWithCause: ZIO[Any, Nothing, String] = failedInt.foldCause(cause => s"this failed with $cause",
    value => s"this succeed with $value")

  val foldedWithCause_v2 = failedInt.foldCauseZIO(
    cause => ZIO.succeed(s"this failed with $cause"),
    value => ZIO.succeed(s"this succeed with $value")
  )

  def callHTTPEndpoint(url: String): ZIO[Any, IOException, String] =
    ZIO.fail(new IOException("no internet, dummy!"))

  val endpointCallWithDefects: ZIO[Any, Nothing, String] =
    callHTTPEndpoint("jakarta,tistory.com").orDie // all erors are now defects

  def callHTTPEndpoinWideError(url: String): ZIO[Any, Exception, String] =
    ZIO.fail(new IOException("no internet!!"))

  def callHTTPEndpoint_v2(url: String): ZIO[Any, IOException, String] =
    callHTTPEndpoinWideError(url).refineOrDie[IOException] {
      case e: IOException => e
      case _: NoRouteToHostException => new IOException(s"No route to host to $url")
    }

  // reverse: ture defects into the error channel
  val endpointCallWithError: ZIO[Any, String, String] = endpointCallWithDefects.unrefine {
    case e => e.getMessage
  }
  trait AppError
  case class IndexError(message: String) // extends AppError
  case class DbError(message: String) // extends AppError
  val callApi: ZIO[Any, IndexError, String] = ZIO.succeed("page: <html></html>")
  val queryDb: ZIO[Any, DbError, Int] = ZIO.succeed(1)

  val combined: ZIO[Any, IndexError | DbError, (String, Int)] = for {
    page <- callApi
    rowsAffected <- queryDb
  } yield (page, rowsAffected)

  val aBadFailure = ZIO.succeed[Int](throw new RuntimeException("this is bad!"))
  val sol1 = aBadFailure.sandbox
  val sol2 = aBadFailure.unrefine{
    case e => e
  }

  def ioException[R, A](zio: ZIO[R, Throwable, A]): ZIO[R, IOException, A] =
    zio.refineOrDie{
      case ioe: IOException => ioe
    }

  def left[R, E, A, B](zio: ZIO[R, E, Either[A, B]]): ZIO[R, Either[E,A], B] =
    zio.foldZIO(
      e => ZIO.fail(Left(e)),
      either => either match {
        case Left(a) => ZIO.fail(Right(a))
        case Right(b) => ZIO.succeed(b)
      }
    )


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = endpointCallWithDefects.debug
}
