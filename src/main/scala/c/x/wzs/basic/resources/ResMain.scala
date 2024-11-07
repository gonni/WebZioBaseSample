package c.x.wzs.basic.resources

import zio._

object ResMain extends ZIOAppDefault {
  
	val finalizer: UIO[Unit] = ZIO.succeed(println("Finalizing!"))

	val finalized: IO[String, Unit] = ZIO.fail("xx_Failed_xx").ensuring(finalizer)

	override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = finalized


	
}
