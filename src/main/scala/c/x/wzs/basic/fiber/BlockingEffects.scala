package c.x.wzs.basic.fiber

import zio._
import c.x.wzs.basic.utils._

object BlockingEffects extends ZIOAppDefault {

  def blockingTask(n: Int): UIO[Unit] = 
    ZIO.succeed(s"running blocking task $n").debugThread *> ZIO.succeed(Thread.sleep(100000)) *> blockingTask(n)

  val program = ZIO.foreachPar(1 to 100)(blockingTask)

  // blocking thread pool
  val aBlockingZIO = ZIO.attemptBlocking {
    println(s"${Thread.currentThread().getName} running a long computation ..")
    Thread.sleep(10000)
    42
  }

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = program

}
