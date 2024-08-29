package c.x.wzs.basic

import zio.Cause

object PlayGround extends App {
  val cause1: Cause[String] = Cause.fail("First failure")
  val cause2: Cause[String] = Cause.die(new RuntimeException("Unexpected error"))
  val combinedCause: Cause[String] = cause1 ++ cause2

  val result = combinedCause.prettyPrint

  println(result)
}
