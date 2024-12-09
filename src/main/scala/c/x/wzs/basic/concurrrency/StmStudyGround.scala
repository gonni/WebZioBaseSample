package c.x.wzs.basic.concurrrency

import zio._

object StmStudyGround extends ZIOAppDefault{

  // def inc(counter: Ref[Int], amount: Int) = for {
  //   c <- counter.get
  //   _ <- counter.set(c + amount)
  // } yield c
  
  def inc(counter: Ref[Int], amount: Int) = counter.updateAndGet(_ + amount)

  val programe = for {
    counter <- Ref.make(0)
    _ <- ZIO.collectAllPar(ZIO.replicate(10)(inc(counter, 1)))
    value <- counter.get
  } yield (value)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = programe.debug
}
