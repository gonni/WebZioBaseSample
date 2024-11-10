package c.x.wzs.basic.fiber

import zio._
import java.util.concurrent.Executors
import c.x.wzs.basic.utils._

object AsyncEffects extends ZIOAppDefault {

  // CALLBACK-based
  object LoginService {
    case class AuthError(message: String)
    case class UserProfile(email: String, name: String)

    // thread pool
    val executor = Executors.newFixedThreadPool(8)

    val passwd = Map(
      "elon@x.com" -> "ElonMersk!1"
    )

    val database = Map(
      "elon@x.com" -> "Daniel"
    )

    def login(email: String, password: String)(onSuccess: UserProfile => Unit, onFailure: AuthError => Unit) = {
      executor.execute { () =>
        println(s"[${Thread.currentThread().getName()}] Attempting login for $email")
        passwd.get(email) match {
          case Some(pw) =>
            onSuccess(UserProfile(email, database(email)))
          case Some(_) =>
            onFailure(AuthError("Incorrect password"))
          case None => 
            onFailure(AuthError(s"User $email doesn't exist. Ploese sign up"))
        }
      }
    }
  }

  def loginAsZIO(id: String, pw: String): ZIO[Any, LoginService.AuthError, LoginService.UserProfile] = 
      ZIO.async[Any, LoginService.AuthError, LoginService.UserProfile] { cb =>  // callback object created by ZIO
        LoginService.login(id,pw)(
          profile => cb(ZIO.succeed(profile)),  // notify the ZIO fiber to complete the ZIO with a success
          error => cb(ZIO.fail(error))  // same, with a failure
        )
      }
    
  val loginProgram = for {
    email <- Console.readLine("Email: ")
    pass <- Console.readLine("Passowrd: ")
    profile <- loginAsZIO(email, pass).debugThread
    _ <- Console.printLine(s"Welcome to X, ${profile.name}")
  } yield()

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = loginProgram
}
