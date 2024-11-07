ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

resolvers += "jitpack" at "https://jitpack.io"
scalacOptions += "-Ymacro-annotations"

lazy val root = (project in file("."))
  .settings(
    name := "WebZioBaseProject",
    version := "1.3.53b",
    assembly / mainClass := Some("c.x.JettyLaunchMain"),
    assembly / assemblyJarName := "nrecc_admin.jar",
  )

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.1.9",
  "dev.zio"       %% "zio-json"            % "0.6.2",
  "dev.zio"       %% "zio-http"            % "3.0.1",
  "io.getquill"   %% "quill-zio"           % "4.8.0",
  "io.getquill"   %% "quill-jdbc-zio"      % "4.8.0",
  "mysql" % "mysql-connector-java" % "8.0.33",
  "dev.zio"       %% "zio-config"          % "4.0.0-RC16",
  "dev.zio"       %% "zio-config-typesafe" % "4.0.0-RC16",
  "dev.zio"       %% "zio-config-magnolia" % "4.0.0-RC16",
  "dev.zio"       %% "zio-logging"       % "2.1.15",
  "dev.zio"       %% "zio-logging-slf4j" % "2.1.15",
  // "org.slf4j"      % "slf4j-simple"      % "2.0.9"
)
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.11"
// libraryDependencies += "org.slf4j" % "slf4j-reload4j" % "1.7.36"
libraryDependencies += "dev.zio" %% "zio-macros" % "2.1.6"

enablePlugins(SbtTwirl)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case PathList("application.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}