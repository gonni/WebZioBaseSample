package c.x.wzs.core

import zio._
import zio.config.typesafe._
// import zio.config._
import zio.config.magnolia.deriveConfig
import java.io.IOException

object HttpServerConfig {
  implicit val config: Config[HttpServerConfig] =
    deriveConfig[HttpServerConfig].nested("HttpServerConfig")
}

case class HttpServerConfig(host: String, port: Int, nThreads: Int)


object ConfMain extends ZIOAppDefault {
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    Runtime.setConfigProvider(
      ConfigProvider.fromResourcePath()
    )
  
  private val serverConfig: ZLayer[Any, Config.Error, HttpServerConfig] =
    ZLayer
      .fromZIO(
        ZIO.config[HttpServerConfig](HttpServerConfig.config).map(identity)
      )

  val workflow: ZIO[HttpServerConfig, Exception, Unit] =
    ZIO.config[HttpServerConfig].flatMap { config =>
      Console.printLine(
        "Application started with following configuration:\n" +
          s"\thost: ${config.host}\n" +
          s"\tport: ${config.port}"
    )
  }

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = 
    workflow.provide(serverConfig)

}
