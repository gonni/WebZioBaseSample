package c.x.wzs.basic.logging

import zio._
import zio.logging.LogFormat
import zio.logging.backend.SLF4J
import zio.logging.fileLogger
import zio.logging.consoleLogger
import zio.config.typesafe.TypesafeConfigProvider

object LogMain extends ZIOAppDefault {
	val logger: ZLogger[String, Unit] =
		new ZLogger[String, Unit] {
			override def apply(
				trace: Trace,
				fiberId: FiberId,
				logLevel: LogLevel,
				message: () => String,
				cause: Cause[Any],
				context: FiberRefs,
				spans: List[LogSpan],
				annotations: Map[String, String]
			): Unit =
				println(s"${java.time.Instant.now()} - ${logLevel.label} - ${message()} : ${spans}")
		}
	
	val configProvider: ConfigProvider = TypesafeConfigProvider.fromHoconFilePath("src/main/resources/logger.conf")

	// override val bootstrap = Runtime.removeDefaultLoggers ++ Runtime.addLogger(logger)  //++ SLF4J.slf4j(LogFormat.colored)
	override val bootstrap: ZLayer[Any, Config.Error, Unit] =
		Runtime.removeDefaultLoggers >>> Runtime.setConfigProvider(configProvider) >>> consoleLogger() >>> fileLogger()
	// override val bootstrap = SLF4J.slf4j(LogFormat.colored)
	// def run = ZIO.log("Application started!")

//   def run = ZIO
//       .dieMessage("Boom!")
//       .foldCauseZIO(
//         cause => ZIO.logErrorCause("application stopped working due to an unexpected error", cause),
//         _ => ZIO.unit
//       )

	case class User(id: String, name: String, profileImage: String)
	def getProfilePicture(username: String) =
		for {
			_ <- ZIO.logSpan("get-profile-picture") {
				for {
					_    <- ZIO.log(s"Getting information of $username from the UserService")
					user <- ZIO.succeed(User("1", "john", "john.png"))
					_    <- ZIO.log(s"Downloading profile image ${user.profileImage}")
					img  <- ZIO.succeed(Array[Byte](1, 2, 3))
					_    <- ZIO.log("Profile image downloaded")
				} yield img
			}
			_ <- ZIO.log("standalone msg ..")
		} yield()
	def run = getProfilePicture("AA")
}
