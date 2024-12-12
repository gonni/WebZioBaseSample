package c.x.wzs.basic.concurrrency

import zio._
import scala.collection.immutable.Stream.Cons
import java.io.IOException
import scala.annotation.tailrec

object DemoScheduler2 extends ZIOAppDefault{
  // Producer: Queue에 랜덤 숫자를 추가하는 함수
  def producer(queue: Queue[Int], signal: Promise[Throwable, Unit]) =
    for {
      _ <- Console.printLine("Producer Start")
      _ <- ZIO.foreach(1 to 10) { _ =>
        for {
          n <- Random.nextIntBounded(100) // 0~99 사이의 랜덤 숫자 생성
          delay <- Random.nextIntBounded(3) // 0~2 사이의 랜덤 숫자 생성
          - <- ZIO.sleep(delay.second) // delay 만큼 대기
          _ <- queue.offer(n)            // Queue에 숫자 추가
          _ <- Console.printLine(s"Produced: $n")
        } yield ()
      }
      _ <- Console.printLine("Producer End")
      _ <- signal.succeed(())
    } yield ()
  
  // Consumer: Queue에서 숫자를 가져와 출력하는 함수
  def consumer(queue: Queue[Int]) =
    for {
      _ <- Console.printLine("Consumer Start")
      _ <- ZIO.foreach(1 to 10) { _ =>
        for {
          n <- queue.take               // Queue에서 숫자 가져오기
          _ <- Console.printLine(s"Consumed: $n")
        } yield ()
      }
      _ <- Console.printLine("Consumer End")
    } yield ()
  
  def consumer2(queue: Queue[Int], signal: Promise[Throwable, Unit]): ZIO[Any, IOException, Unit] = for {
    _ <- Console.printLine("Producer Processing")
    t <- queue.take
    _ <- Console.printLine(s"Produced: $t")
    _ <- signal.isDone.flatMap {
      case true => 
        Console.printLine("Got finish signal in Consumer ..") *> ZIO.unit
      case false => consumer2(queue, signal)
    }
  } yield ()

  def consumer3(queue: Queue[Int], signal: Promise[Throwable, Unit]): ZIO[Any, IOException, Unit] = {
    def cons() = for {
      _ <- Console.printLine("Producer Processing")
      t <- queue.take
      _ <- Console.printLine(s"Produced: $t")
    } yield ()

    for {
      _ <- signal.isDone.flatMap {
        case true => 
          Console.printLine("Got finish signal in Consumer ..") *> ZIO.unit
        case false => 
          cons() *> consumer3(queue, signal)
      }
    } yield ()
  }

  def consumer5(queue: Queue[Int], signal: Promise[Throwable, Unit]): ZIO[Any, Throwable, Unit] = {
    for {
      _ <- Console.printLine("Consumer start")
      _ <- (for {
        t <- queue.take
        _ <- Console.printLine(s"Consumed: $t")
      } yield ()).forever.race(signal.await)
      _ <- Console.printLine("Consumer end")
    } yield ()
  }
  
  val app = for {
      // Bounded Queue 생성
      signal <- Promise.make[Throwable, Unit]
      queue <- Queue.unbounded[Int]  // Producer와 Consumer 실행
      // producerFiber <- producer(queue).fork // Producer 비동기로 실행
      // consumerFiber <- consumer(queue).fork // Consumer 비동기로 실행
      allFiber <- producer(queue, signal).zipPar(consumer5(queue, signal)).fork
      // _ <- producerFiber.join
      // _ <- consumerFiber.join
      _ <- allFiber.join
      _ <- Console.printLine("All fibers finished ..")
    } yield ExitCode.success

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = app
}
