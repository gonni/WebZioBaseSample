package c.x.wzs.basic.concurrrency

import zio._
import c.x.wzs.basic.utils.debugThread
import org.scalafmt.config.Docstrings.BlankFirstLine.yes

object SemaphorePlayground extends ZIOAppDefault {

  val aSemaphore = Semaphore.make(10)
  
  def doWorkWhileLoggedIn(): UIO[Int] = 
    ZIO.sleep(1.second) *> Random.nextIntBounded(100)

  def login(id: Int, sem: Semaphore): UIO[Int] = 
    ZIO.succeed(s"[task $id] waiting for semaphore...").debugThread *>
    sem.withPermit{
      for {
        _ <- ZIO.succeed(s"[task $id] logged in,  working ..").debugThread
        res <- doWorkWhileLoggedIn()
        _ <- ZIO.succeed(s"[task $id] done: $res").debugThread
      } yield res
    }
  
  def demoSemaphone() = for{
    sem <- Semaphore.make(1)
    f1 <- login(1, sem).fork
    f2 <- login(2, sem).fork
    f3 <- login(3, sem).fork
    _ <- f1.join
    _ <- f2.join
    _ <- f3.join
  } yield ()
  
  def loginWeighted(n: Int, sem: Semaphore): UIO[Int] = 
    ZIO.succeed(s"[task $n] waiting to log in with $n permits").debugThread *>
    sem.withPermits(n){
      for {
        // critical section starts when you acquire ALL n permits
        _ <- ZIO.succeed(s"[task $n] logged in,  working ..").debugThread
        res <- doWorkWhileLoggedIn()
        _ <- ZIO.succeed(s"[task $n] done: $res").debugThread
      } yield res
    }
  
  val mySemaphore = Semaphore.make(1) // mutex  
  val tasks = ZIO.collectAllPar((1 to 10).map{ id =>
    for {
      sem <- mySemaphore
      _ <- ZIO.succeed(s"[task $id] waiting to log in").debugThread
      res <- sem.withPermit {
        for {
          _ <- ZIO.succeed(s"[task $id] logged in,  working ..").debugThread
          res <- doWorkWhileLoggedIn()
          _ <- ZIO.succeed(s"[task $id] done: $res").debugThread
        } yield res
      }
    } yield res
  })

  val taskFixed = mySemaphore.flatMap { sem =>  // only one instance of semaphore
    ZIO.collectAllPar((1 to 10).map{ id =>
      for {
        // sem <- mySemaphore
        _ <- ZIO.succeed(s"[task $id] waiting to log in").debugThread
        res <- sem.withPermit {
          for {
            _ <- ZIO.succeed(s"[task $id] logged in,  working ..").debugThread
            res <- doWorkWhileLoggedIn()
            _ <- ZIO.succeed(s"[task $id] done: $res").debugThread
          } yield res
        }
      } yield res
    })
  }

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = tasks.debugThread
}
