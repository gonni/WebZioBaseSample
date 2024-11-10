package c.x.wzs.basic.coordination

import zio._
import zio.stm._

object TxEffects extends ZIOAppDefault {

  val anSTM: ZSTM[Any, Nothing, Int] = STM.succeed(42)
  val aFailedSTM = STM.fail("something bad")
  val anAttemptSTM: ZSTM[Any, Throwable, Int] = STM.attempt(42/0)

  // type aliases
  val ustm: USTM[Int] = STM.succeed(2)
  val anSTM_v2: STM[Nothing, Int] = STM.succeed(42)

  
  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = ???
}
