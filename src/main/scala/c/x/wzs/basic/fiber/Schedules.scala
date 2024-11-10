package c.x.wzs.basic.fiber

import zio._
import c.x.wzs.basic.utils._

object Schedules extends ZIOAppDefault {

  val aZIO = Random.nextBoolean.flatMap { flag =>
    if(flag) ZIO.succeed("fetched value!").debugThread
    else ZIO.succeed("failure ...").debugThread *> ZIO.fail("error")
  }

  val aRetriedZIO = aZIO.retry(Schedule.recurs(10))

  // schedules
  val oneTimeSchedule = Schedule.once
  val recurrentSchedule = Schedule.recurs(10)
  val fixedIntervalSchedule = Schedule.spaced(1.second) // can be never end
  // exponetial backoff
  val exBackoffSchedule = Schedule.exponential(1.second, 2.0) // 1, 4, 8 ..
  val fiboSchedule = Schedule.fibonacci(1.second) // 1,1,2,3,5 ..

  // combibators
  val recurrentAndSpaced = Schedule.recurs(3) && Schedule.spaced(1.second)  // every attemp is 1s aprt, 3 attempts total
  // sequencingg
  val recurrentThenSpaced = Schedule.recurs(3) ++ Schedule.spaced(1.second) // 3 retiries, then every 1s

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = aRetriedZIO
}
