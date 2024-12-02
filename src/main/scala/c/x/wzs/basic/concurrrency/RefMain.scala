package c.x.wzs.basic.concurrrency

import zio._

object RefMain extends ZIOAppDefault {

  def request(counter: Ref[Int]): ZIO[Any, Nothing, Unit] =
    for {
      _ <- counter.update(_ + 1)
      count <- counter.get
      _ <- Console.printLine(s"Request number: $count").orDie
    } yield ()

  private val initial = 0
  private val myApp = 
    for {
      ref <- Ref.make(initial)
      _ <- request(ref) zipPar request(ref)
      rn <- ref.get
      _ <- Console.printLine(s"Final count is: $rn").orDie
    } yield ()
  
  def repeat[E, A](n: Int)(io: IO[E, A]): IO[E, Unit] =
    Ref.make(0).flatMap { iRef =>
      def loop: IO[E, Unit] = iRef.get.flatMap { i =>
        if (i < n)
          io *> iRef.update(_ + 1) *> loop
        else
          ZIO.unit
      }
      loop
    }

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = repeat(10)(Ref.make(10)).exitCode
}
