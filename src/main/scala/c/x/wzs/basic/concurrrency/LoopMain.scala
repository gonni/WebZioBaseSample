package c.x.wzs.basic.concurrrency

import zio._

object LoopMain extends ZIOAppDefault {

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

  override def run = repeat(100)(Console.printLine("Hello, World!")).exitCode
}
