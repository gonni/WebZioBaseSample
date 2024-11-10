package c.x.wzs.basic.fiber

import zio.*
import Console._

object Fiber2 extends ZIOAppDefault{

	val uninterruptibleApp = for {
		fiber <- Clock.currentDateTime
			.flatMap(time => Console.printLine(time))
			.schedule(Schedule.fixed(1.seconds))
			.uninterruptible
			.fork
		_ <- ZIO.succeed(Thread.sleep(5000L))
		_     <- fiber.interrupt // Runtime stuck here and does not go further
	} yield ()

	val finalizationApp = for {
		fiber <- Console.printLine("Working on the first job")
			.schedule(Schedule.fixed(1.seconds))
			.ensuring {
				(printLine(
					"Finalizing or releasing a resource that is time-consuming"
				) *> ZIO.sleep(7.seconds)).orDie
			}.fork
		_     <- fiber.interrupt.delay(4.seconds)
		_     <- printLine(
						"Starting another task when the interruption of the previous task finished" )
	} yield ()

	// ---
	val fastInterruptionApp = 
	for {
		fiber <- printLine("Working on the first job")
			.schedule(Schedule.fixed(1.seconds))
			.ensuring {
				(printLine(
					"Finalizing or releasing a resource that is time-consuming"
				) *> ZIO.sleep(7.seconds)).orDie
			}
			.fork
		_ <- fiber.interruptFork.delay(4.seconds) // fast interruption
		_ <- printLine(
			"Starting another task while interruption of the previous fiber happening in the background"
		)
	} yield ()
	override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = fastInterruptionApp
}
