package c.x.wzs.basic.errorhandle
import zio.*

import java.io.IOException
import java.net.NoRouteToHostException

object ErrorExamples extends ZIOAppDefault {

  val badZIO : ZIO[Any, Exception, Int] = ZIO.succeed {
    println("Trying something")
    val string: String = null
    string.length
  }

  val badZIOd = badZIO.orDie
  /**
   * Errors = failures present in the ZIO type signature ("checked" exception")
   * Defects = failure that are unrecoverable, unforeseen, NOT present in the ZIO type signature
   *
   * ZIO[R,E,A] can finish with Exit[E, A]
   * - Success[A] containing a value
   * - Cause[E]
   *  - Fail[E] containing the error
   *  - Die(t: Throwable) which was unforeseen
   */

  // sandbox
  val failedInt: ZIO[Any, String, Int] = ZIO.fail("I failed!")
//  <FAIL>Fail(Fail(I failed!,Stack trace for thread "zio-fiber-669256248":
//    at c.x.wzs.basic.ErrorExamples.failedInt(ErrorExamples.scala:28)
//    at c.x.wzs.basic.ErrorExamples.failureCauseExcetpion(ErrorExamples.scala:29)
//    at c.x.wzs.basic.ErrorExamples.run(ErrorExamples.scala:90)),Stack trace for thread "zio-fiber-669256248":
//    at c.x.wzs.basic.ErrorExamples.failureCauseExcetpion(ErrorExamples.scala:29)
//    at c.x.wzs.basic.ErrorExamples.run(ErrorExamples.scala:90))
//    timestamp=2024-08-29T00:26:08.328892Z level=ERROR thread=#zio-fiber-458073053 message="" cause="Exception in thread "zio-fiber-669256248" zio.Cause$Fail$$anon$18: Fail(I failed!,Stack trace for thread "zio-fiber-669256248":
//    at c.x.wzs.basic.ErrorExamples.failedInt(ErrorExamples.scala:28)
//    at c.x.wzs.basic.ErrorExamples.failureCauseExcetpion(ErrorExamples.scala:29)
//    at c.x.wzs.basic.ErrorExamples.run(ErrorExamples.scala:90))
//    at c.x.wzs.basic.ErrorExamples.failureCauseExcetpion(ErrorExamples.scala:29)
//    at c.x.wzs.basic.ErrorExamples.run(ErrorExamples.scala:90)"

  val failureCauseExcetpion: ZIO[Any, Cause[String], Int] = failedInt.sandbox
//  <FAIL>Fail(Fail(I failed!,Stack trace for thread "zio-fiber-1791166274":
//    at c.x.wzs.basic.ErrorExamples.failedInt(ErrorExamples.scala:28)
//    at c.x.wzs.basic.ErrorExamples.failureCauseExcetpion(ErrorExamples.scala:42)
//    at c.x.wzs.basic.ErrorExamples.run(ErrorExamples.scala:106)),Stack trace for thread "zio-fiber-1791166274":
//    at c.x.wzs.basic.ErrorExamples.failureCauseExcetpion(ErrorExamples.scala:42)
//    at c.x.wzs.basic.ErrorExamples.run(ErrorExamples.scala:106))
//    timestamp=2024-08-29T00:27:52.637218Z level=ERROR thread=#zio-fiber-914842570 message="" cause="Exception in thread "zio-fiber-1791166274" zio.Cause$Fail$$anon$18: Fail(I failed!,Stack trace for thread "zio-fiber-1791166274":


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

  val app1 = failureCauseExcetpion.mapError(e => e.failures.toString())
  val app2 = failureCauseExcetpion.fold(failure = a => a.failures.mkString("|"), success = a => a)
  val app3 = failureCauseExcetpion.foldZIO(
    failure = a => ZIO.fail(a.failures.mkString("|")), success = a => ZIO.succeed(a))
  val app4 = failureCauseExcetpion.foldCause(failure = ccs => ccs.failures.mkString("|"), success = a => "" + a)
  val app5 = failureCauseExcetpion.flip.cause.map(c => println(c.prettyPrint))

  val app6 = failureCauseExcetpion.catchAllCause {
    cause => ZIO.succeed[String](s"Handled failure: ${cause.prettyPrint}")
  }

  val app7 = failureCauseExcetpion.cause
  val app8 = failedInt.ensuring(ZIO.die(throw new Exception("catch Failed .."))).cause

  val myApp =
    ZIO.fail("first")
      .ensuring(ZIO.die(throw new Exception("second")))

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = app8.debug
}
