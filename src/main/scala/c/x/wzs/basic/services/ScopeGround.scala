package c.x.wzs.basic.services

import zio._

object ScopeGround extends ZIOAppDefault{

//  val app = for {
//    scope <- Scope.make
//    _ <- ZIO.debug("Scope is created!")
//    _ <- scope.addFinalizer(
//      for {
//        _ <- ZIO.debug("The finalizer is started!")
//        _ <- ZIO.sleep(5.seconds)
//        _ <- ZIO.debug("The finalizer is done!")
//      } yield ()
//    )
//    _ <- ZIO.debug("Leaving scope!")
//    _ <- scope.close(Exit.succeed(()))
//    _ <- ZIO.debug("Scope is closed!")
//  } yield ()

  val resource1: ZIO[Scope, Nothing, Unit] =
    ZIO.acquireRelease(ZIO.debug("Acquiring the resource 1"))(_ =>
      ZIO.debug("Releasing the resource one") *> ZIO.sleep(5.seconds)
    )
  val resource2: ZIO[Scope, Nothing, Unit] =
    ZIO.acquireRelease(ZIO.debug("Acquiring the resource 2"))(_ =>
      ZIO.debug("Releasing the resource two") *> ZIO.sleep(3.seconds)
    )

  val app =
    ZIO.scoped(
      for {
        scope <- ZIO.scope
        _ <- ZIO.debug("Entering the main scope!")
        _ <- scope.addFinalizer(ZIO.debug("Releasing the main resource!") *> ZIO.sleep(2.seconds))
        _ <- scope.extend(resource1)
        _ <- scope.extend(resource2)
        _ <- ZIO.debug("Leaving scope!")
      } yield ()
    )


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = app.debug
}
