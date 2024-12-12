package c.x.wzs.basic.concurrrency

import zio._
import java.util.concurrent.TimeUnit
import java.lang.System.currentTimeMillis

object DemoScheduler extends ZIOAppDefault {

  val taskDownloadData = (url: String) => for {
    randDelay <- Random.nextIntBounded(3)
    _ <- ZIO.sleep(Duration(randDelay, TimeUnit.SECONDS))
    data <- ZIO.succeedBlocking(s"Data downloaded from $url after $randDelay seconds ${currentTimeMillis()}")
    _ <- ZIO.succeed(println(data))
  } yield ()

  val semTaskDownloadData = (sem: Semaphore, url: String) => for {
    _ <- sem.withPermit(taskDownloadData(url))
  } yield ()

  val mySemaphore = Semaphore.make(10) // limit to 10 fibers

  val urls = List(
    "http://example.com/1",
    "http://example.com/2",
    "http://example.com/3",
    "http://example.com/4",
    "http://example.com/5",
    "http://example.com/6",
    "http://example.com/7",
    "http://example.com/8",
    "http://example.com/9",
    "http://example.com/10"
  )

  // producer는 crawling 대상 종목을 선별하여 queue에 넣는다.
  val producer1 = for {
    queue <- Queue.unbounded[String]
    _ <- ZIO.foreach(urls)(url => queue.offer(url))
  } yield queue

  val producer = for {
    queue <- Queue.unbounded[String]
    _ <- ZIO.foreach(urls)(url => queue.offer(url))
  } yield queue

  val consumer = (queue: Queue[String], sem: Semaphore) => for {
    _ <- (for {
      url <- queue.take
      _ <- semTaskDownloadData(sem, url)
    } yield ()).repeat(Schedule.forever)
  } yield ()

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = for {
    queue <- producer
    sem <- mySemaphore
    _ <- ZIO.forkAll(List.fill(10)(consumer(queue, sem)))
  } yield ()
}