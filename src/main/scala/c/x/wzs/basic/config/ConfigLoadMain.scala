package c.x.wzs.basic.config

import zio._

import zio.config._
import zio.config.typesafe._
import java.io.IOException

import zio.config.magnolia.deriveConfig

object HttpServerConfig {
  implicit val config: Config[HttpServerConfig] =
    deriveConfig[HttpServerConfig].nested("HttpServerConfig")
}

case class HttpServerConfig(host: String, port: Int, nThreads: Int)

object ConfigLoadMain extends ZIOAppDefault {
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    Runtime.setConfigProvider(
      ConfigProvider.fromResourcePath()
    )

  private val serverConfig: ZLayer[Any, Config.Error, HttpServerConfig] = 
    ZLayer.fromZIO(
      ZIO.config[HttpServerConfig](HttpServerConfig.config).map(identity)
    )

    
  val workflow: ZIO[HttpServerConfig, IOException, Unit] =
    ZIO.service[HttpServerConfig].flatMap { config =>
      Console.printLine(
        "Application started with following configuration:\n" +
          s"\thost: ${config.host}\n" +
          s"\tport: ${config.port}"
      )
    }

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = 
    workflow.provide(serverConfig).exitCode

  
}
