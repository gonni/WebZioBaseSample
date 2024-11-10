package c.x.wzs.basic.fiber

import zio._
import c.x.wzs.basic.utils._

object Interruptions extends ZIOAppDefault{

	val zioWithTime = 
		(
			ZIO.succeed("starting computation").debugThread *> 
			ZIO.sleep(2.second) *>
			ZIO.succeed(45).debugThread
		).onInterrupt(ZIO.succeed("I was interrupted").debugThread)

	val interruption = for {
		fib <- zioWithTime.fork
		_ <- ZIO.sleep(1.second) *> ZIO.succeed("Interrupting!").debugThread *> fib.interrupt
		_ <- ZIO.succeed("Interruption succeessful").debugThread
		result <- fib.join
	} yield()

	val interruption_v2 = for {
		fib <- zioWithTime.fork
		_ <- ZIO.sleep(1.second) *> ZIO.succeed("Interrupting!").debugThread *> fib.interruptFork
		_ <- ZIO.succeed("Interruption succeessful").debugThread
		result <- fib.join
	} yield()

	// Automatic interruption
	// outliving a parent fiber
	val parentEffect = ZIO.succeed("spawing fiber").debugThread *>
		// zioWithTime.fork *>	// child fiber
		zioWithTime.forkDaemon *>	// this fiber will now be a child of the MAIN fiber
		ZIO.sleep(1.second) *>
		ZIO.succeed("parent successful").debugThread
	
		val testOutlivingParent = for {
			parentEffectFib <- parentEffect.fork
			_ <- ZIO.sleep(3.seconds)
			_ <- parentEffectFib.join
		} yield()
	// child fibers will be (automatically) interrupted if the parent fiber is completed
	
	// racing
	val slowEffect = (ZIO.sleep(2.seconds) *> ZIO.succeed("slow").debugThread)
		.onInterrupt(ZIO.succeed("[Slow] interrupted").debugThread)
	val fastEffect = (ZIO.sleep(1.seconds) *> ZIO.succeed("fast").debugThread)
		.onInterrupt(ZIO.succeed("[Fast] interrupted").debugThread)
	
	val aRace = slowEffect.race(fastEffect)
	val testRace = aRace.fork *> ZIO.sleep(3.seconds)
	
	// Excercise
	def timeout[R,E,A](zio: ZIO[R,E,A], time: Duration): ZIO[R,E,A] = for {
		fib <- zio.fork
		_ <- (ZIO.sleep(time) *> fib.interrupt).fork
		result <- fib.join
	} yield result

	// Excercise
	def timeout_v2[R,E,A](zio: ZIO[R,E,A], time: Duration): ZIO[R,E,Option[A]] = 
		timeout(zio, time).foldCauseZIO(
			cause => if (cause.isInterrupted) ZIO.succeed(None) else ZIO.failCause(cause),
			value => ZIO.succeed((Some(value)))
	)

	val testTimeout_v2 = timeout_v2(
		ZIO.succeed("Starting").debugThread *> ZIO.sleep(2.seconds) *> ZIO.succeed("I made it!").debugThread,
		1.second
	).debugThread

	override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = testTimeout_v2
}
