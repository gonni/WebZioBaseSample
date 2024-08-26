package c.x.wzs.basic

import zio._

object ErrorSample extends ZIOAppDefault {

  import zio._

  def divide(a: Int, b: Int): ZIO[Any, ArithmeticException, Int] =
    if (b == 0)
      ZIO.fail(new ArithmeticException("divide by zero"))
    else
      ZIO.succeed(a / b)

  def divide1(a: Int, b: Int): ZIO[Any, Nothing, Int] =
    if (b == 0)
      ZIO.die(new ArithmeticException("divide by zero")) // Unexpected error
    else
      ZIO.succeed(a / b)


  def parseInt(input: String): ZIO[Any, Throwable, Int] =
    ZIO.attempt(input.toInt) // ZIO[Any, Throwable, Int]
      .refineToOrDie[NumberFormatException] // ZIO[Any, NumberFormatException, Int]

  val effect: ZIO[Any, DomainError, Unit] =
    ZIO.fail(Baz("Oh uh!"))

  val refined: ZIO[Any, DomainError, Unit] =
    effect.refineOrDie {
      case foo: Foo => foo
      case bar: Bar => bar
    }



  val app = refined.catchAll(_ => ZIO.unit)
//    parseInt("S")
    //ZIO.succeed(println("Ahehe")) *> ZIO.die(new ArithmeticException("divide by zero"))

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = Console.printLine("Hello World").orDie.debug
}

sealed abstract class DomainError(msg: String)
  extends Exception(msg)
    with Serializable
    with Product
case class Foo(msg: String) extends DomainError(msg)
case class Bar(msg: String) extends DomainError(msg)
case class Baz(msg: String) extends DomainError(msg)
//--

type Name = String

enum StorageError extends Exception {
  case ObjectExist(name: Name)            extends StorageError
  case ObjectNotExist(name: Name)         extends StorageError
  case PermissionDenied(cause: String)    extends StorageError
  case StorageLimitExceeded(limit: Int)   extends StorageError
  case BandwidthLimitExceeded(limit: Int) extends StorageError
}