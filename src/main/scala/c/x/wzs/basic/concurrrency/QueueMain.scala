package c.x.wzs.basic.concurrrency

import zio._

object QueueMain extends ZIOAppDefault {

  // val app = for {
  //   queue <- Queue.bounded[Int](100)
  //   _ <- queue.offer(1)
  //   _ <- queue.offer(2)
  //   _ <- queue.offer(3)
  //   _ <- queue.take.flatMap(Console.printLine(_))
  //   _ <- queue.take.flatMap(Console.printLine(_))
  //   _ <- queue.take.flatMap(Console.printLine(_))
  // } yield queue

  val polled = for {
    queue <- Queue.bounded[Int](100)
    _ <- queue.offer(10)
    _ <- queue.offer(20)
    head <- queue.poll
  } yield queue.takeAll

  val taken: UIO[Chunk[Int]] = for {
    queue <- Queue.bounded[Int](100)
    _ <- queue.offer(10)
    _ <- queue.offer(20)
    chunk  <- queue.takeUpTo(5)
  } yield chunk

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = taken.debug
}
