package c.x.wzs.basic.fiber

import zio._
import c.x.wzs.basic.utils.*
import java.util.Scanner
import java.io.File

object Resources extends ZIOAppDefault {

	// feature	
	def unsafeMethod(): Int = throw new RuntimeException("Not an int here for you")
	val anAttemp = ZIO.attempt(unsafeMethod())

	val attemptWithFinalizer = anAttemp.ensuring(ZIO.succeed("finerizer").debugThread)
	val attemptWith2Finalizer = attemptWithFinalizer.ensuring(ZIO.succeed("another finalizer!").debugThread)

	//.onInterrupt, onError, onDone, onExit


	// resource lifecycle
	class Connection(url: String) {
		def open() = ZIO.succeed(s"opening connection to $url ...").debugThread
		def close() = ZIO.succeed(s"closing connnection to $url ...").debugThread
	}

	object Connection {
		def create(url: String) = ZIO.succeed(new Connection(url))
	}

	val fetchUrl = for {
		conn <- Connection.create("rockthejvm.com")
		fib <- (conn.open() *> ZIO.sleep(300.seconds)).ensuring(conn.close()).fork
		_ <- ZIO.sleep(1.second) *> ZIO.succeed("interrupting").debugThread *> fib.interrupt
		_ <- fib.join
	} yield ()

	// acqquireRelease
	val cleanConnection = ZIO.acquireRelease(Connection.create("rockthejvm.com"))(_.close())
	val fechWithResource = for {
		conn <- cleanConnection
		fib <- (conn.open() *> ZIO.sleep(300.seconds)).fork
		_ <- ZIO.sleep(1.second) *> ZIO.succeed("interrupting").debugThread *> fib.interrupt
		_ <- fib.join
	} yield ()

	val fetchWithScopedResource = ZIO.scoped(fechWithResource)

	// acquireReleaseWith
	val cleanConnection_v2 = ZIO.acquireReleaseWith(Connection.create("rt.com"))(_.close())(conn => conn.open() *> ZIO.sleep(300.seconds))
	
	val feachWithResource_v2 = for {
		fib <- cleanConnection_v2.fork
		_ <- ZIO.sleep(1.second) *> ZIO.succeed("interrupting").debugThread *> fib.interrupt
		_ <- fib.join
	} yield()

	def openFineScanner(path: String): UIO[Scanner] = 
		ZIO.succeed(new Scanner(new File(path)))

	def readLineByLine(scanner: Scanner): UIO[Unit] = 
					if(scanner.hasNextLine)
						(ZIO.succeed(scanner.nextLine()).debugThread *> ZIO.sleep(100.millis) *> readLineByLine(scanner))
					else
						ZIO.unit

	def acquireOpenFile(path: String): UIO[Unit] = 
		ZIO.succeed(s"opening file at $path").debugThread *> 	
			ZIO.acquireReleaseWith(openFineScanner(path))(scanner => ZIO.succeed(scanner.close()))(readLineByLine)


	// acquireRelease vs acquireReleaseWith
	def connFromCOnfig(path: String): UIO[Unit] = 
		ZIO.acquireReleaseWith(openFineScanner(path))(scanner => ZIO.succeed("closing file").debugThread *> ZIO.succeed(scanner.close())){scanner =>
			ZIO.acquireReleaseWith(Connection.create(scanner.nextLine()))(_.close()) { conn =>
				conn.open() *> ZIO.never
			}
		}



	override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = fechWithResource
}
