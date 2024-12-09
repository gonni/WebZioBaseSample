package c.x.wzs.basic.concurrrency

import zio._
import zio.stm._

object TransactionalEffects extends ZIOAppDefault {
  // STM = "automic effects"
  val anSTM: ZSTM[Any, Nothing, Int] = STM.succeed(1)
  val aFailedSTM: ZSTM[Any, String, Int] = STM.fail("something bad")
  val anAttemptSTM: ZSTM[Any, Throwable, Int] = STM.attempt(42/0)
  
  val ustm: USTM[Int] = STM.succeed(2)
  val onSTM_v2: STM[Nothing, Int] = STM.succeed(3)
  
  val anAtomicEffect = anAttemptSTM.commit
  
  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = ???
}
