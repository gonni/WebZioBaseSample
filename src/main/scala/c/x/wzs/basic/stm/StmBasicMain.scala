package c.x.wzs.basic.stm

import zio._

object StmBasicMain extends ZIOAppDefault {
  def inc(counter: Ref[Int], amount: Int) = for {
    c <- counter.get
    _ <- counter.set(c + amount)
  } yield c

  val program = for {
    counter <- Ref.make(0)
    _ <- ZIO.collectAllPar(ZIO.replicate(10)(inc(counter, 1)))
    value <- counter.get
  } yield (value)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = program.debug
  
}
