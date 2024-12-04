package c.x.wzs.basic.concurrrency

import zio._
import c.x.wzs.basic.utils.debugThread
import org.scalafmt.config.Docstrings.BlankFirstLine.yes
import scala.collection.immutable.Queue

abstract class Mutex {
  def acquire: UIO[Unit]
  def release: UIO[Unit]
}

object Mutex {
  type Signal = Promise[Nothing, Unit]
  case class State(locked: Boolean, waiting: Queue[Signal])
  val unlocked = State(locked = false, Queue.empty)

  def make: UIO[Mutex] = Ref.make(unlocked).map { (state: Ref[State]) =>
    new Mutex {
      override def acquire: UIO[Unit] = Promise.make[Nothing, Unit].flatMap { signal =>
        state.modify {
          case State(false, _) => ZIO.unit -> State(locked = true, Queue.empty)
          case State(true, waiting) => signal.await -> State(locked = true, waiting.enqueue(signal))
        }.flatten
      }
      override def release: UIO[Unit] = ???
    }

  }
} 

object MutexPlayground extends ZIOAppDefault {

  def workInCriticalRegion(): UIO[Int] = 
    ZIO.sleep(1.second) *> Random.nextIntBounded(100)

  def demoNonLockingTests() = 
    ZIO.collectAllParDiscard((1 to 10).toList.map { i =>
      for {
        _ <- ZIO.succeed(s"[task $i] working ..").debugThread
        result <- workInCriticalRegion()
        _ <- ZIO.succeed(s"[task $i] get result: $result").debugThread
      } yield ()
    })
  
  def createTask(id: Int, mutex: Mutex): UIO[Int] = for {
    _ <- ZIO.succeed(s"[task $id] waiting for mutex...").debugThread
    _ <- mutex.acquire
    _ <- ZIO.succeed(s"[task $id] working ..").debugThread
    result <- workInCriticalRegion()
    _ <- ZIO.succeed(s"[task $id] get result: $result").debugThread
    _ <- mutex.release
  } yield result

  def demoLockingTasks() = for {
    mutex <- Mutex.make
    _ <- ZIO.collectAllParDiscard((1 to 10).toList.map { i =>
      createTask(i, mutex)
    }) 
  } yield ()

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = demoLockingTasks().debug
}
