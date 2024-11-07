package c.x.wzs.basic.services

import zio._
object ServiceBasic extends ZIOAppDefault {

	val zio1 = ZIO.succeed(1)
	val zio2 = ZIO.succeed(2)
	
	val sum = for {
		a <- zio1
		b <- zio2
	} yield (a + b)

	val zioHellMsg = ZIO.succeed("Hello World") 
	val app = for {
		msg <- zioHellMsg
		v <- sum
		_ <- Console.printLine(msg)
		_ <- Console.printLine("sum = " + v)
	} yield ()

	val zio0 = ZIO.succeed(0)

	val div: ZIO[Any, Exception, Int] = for {
		a <- zio1
		b <- zio0
	} yield (a / b)
	override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = 
		div.debug
}
