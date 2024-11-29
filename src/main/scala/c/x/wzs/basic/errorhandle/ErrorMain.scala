package c.x.wzs.basic.errorhandle

import zio._
import java.io.IOException
import java.net.NoRouteToHostException

object ErrorMain extends ZIOAppDefault {
	val divisionByZero = ZIO.succeed(1 / 0).sandbox
	
	val failedInt = ZIO.fail("I failed!")
	val failureCauseExposed: ZIO[Any, Cause[String], Int] = failedInt.sandbox
	val failureCauseHidden: ZIO[Any, String, Int] = failureCauseExposed.unsandbox

	// fold with cause
	val foldedWithCause = failedInt.foldCause(
		cause => s"this failed with ${cause.defects}",
		value => s"this succeeded with $value"
	)

	val foldedWithCause_v2: ZIO[Any, Nothing, String] = failedInt.foldCauseZIO(
		cause => ZIO.succeed(s"this failed with ${cause.defects}"),
		value => ZIO.succeed(s"this succeeded with $value")
	)

	/**
		* Good practive
		* - at a lower level, your "errors" should be treated
		* - at a higher level, you should hide "error" and assume they are unrecoverable
		*/
	def callHTTPEndpoint(url: String): ZIO[Any, IOException, String] = 
		ZIO.fail(new IOException("no internet connection"))

	val endpointCallWithDefects: ZIO[Any, Nothing, String] = callHTTPEndpoint("rockthejvm.com").orDie //.catchAll(_ => ZIO.succeed("Failed"))

	// refining the error channel
	def callHTTPEndpointWideError(url: String): ZIO[Any, Exception, String] = 
		ZIO.fail(new IOException("no internet connection"))
	
	def callHTTPEndpoint_v2(url: String) : ZIO[Any, IOException, String] = 
		callHTTPEndpointWideError(url).refineOrDie[IOException] {
			case e: IOException => e
			case _: NoRouteToHostException => new IOException(s"No route to host to $url, can't fetch page")
		}
	
		// reverse: turn defects into the error channel
	val endpointCallWithError: ZIO[Any, String, String] = endpointCallWithDefects.unrefine {
		case e => e.getMessage
	}

	/*
	  Combine effects with different errors 
	*/
	trait AppError
	case class IndexError(message: String) extends AppError
	case class DbError(message: String) extends AppError
	val callApi: ZIO[Any, IndexError, String] = ZIO.succeed("page: <html></html>") 
	val queryDB: ZIO[Any, DbError, Int] = ZIO.succeed(1)
	val combined: ZIO[Any, IndexError | DbError, (String, Int)] = for {
		page <- callApi
		rowsAffected <- queryDB
	} yield (page, rowsAffected)	// lost type safety

	/*
		Solutions: 
			- desugb ab error model
			- use Scala3 union type
			- .mapError to some common error type
	*/

	/**
	  * Exercise
	  */
	
	val aBadFailure = ZIO.succeed[Int](throw new RuntimeException("this is bad!"))
	val aBadFailureAnswer = aBadFailure.unrefine{
		e => e.getMessage
	}
	
	def ioException[R, A](zio: ZIO[R, Throwable, A]): ZIO[R, IOException, A] = 
		zio.refineOrDie[IOException]{
			case ioe: IOException => ioe 
		}
	
	// 3
	def left[R, E, A, B](zio: ZIO[R,E,Either[A,B]]): ZIO[R, Either[E, A], B] = 
		zio.foldZIO(
			e => ZIO.fail(Left(e)),
			either => either match {
				case Left(a) => ZIO.fail(Right(a))
				case Right(b) => ZIO.succeed(b)
			}
		)

  
	// 4
	val database = Map (
		"dailel" -> 123,
		"alice" -> 789
	)
	case class QueryError(reason: String)
	case class UserProfile(name: String, phone: Int)

	def lookupProfile(userId: String): ZIO[Any, QueryError, Option[UserProfile]] =
		if(userId != userId.toLowerCase()) 
			ZIO.fail(QueryError("User ID format invalid"))
		else 
			ZIO.succeed(database.get(userId).map(phone => UserProfile(userId, phone)))
	
	def betterLookupProfile(userId: String): ZIO[Any, Option[QueryError] ,UserProfile] = 
		lookupProfile(userId).foldZIO(
			error => ZIO.fail(Some(error)),
			profileOption => profileOption match {
				case Some(profile) => ZIO.succeed(profile)
				case None => ZIO.fail(None)
			}
		)
	
	def betterLookupProfile_v2(userId: String) = lookupProfile(userId).some

	override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = ???
}
