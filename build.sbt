name := "ZIO_test"

version := "0.1"

scalaVersion := "2.12.10"

val circeVersion = "0.12.0"
val doobieVersion = "0.8.2"
val h2Version = "1.4.197"
val http4sVersion = "0.20.10"
val monixVersion      = "3.0.0"
val zioVersion      = "1.0.0-RC11"

mainClass in (Compile, run) := Some("com.Boot")

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions := Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "dev.zio"    %% "zio" % zioVersion,
  "dev.zio"    %% "zio-streams" % zioVersion,
  "dev.zio"    %% "zio-interop-reactivestreams" % "1.0.2.0-RC1",

  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe"        % http4sVersion,

  "org.tpolecat"          %% "doobie-core"          % doobieVersion,
  "org.tpolecat"          %% "doobie-h2"            % doobieVersion,
  "org.tpolecat"          %% "doobie-hikari"        % doobieVersion,

  "io.circe" %% "circe-core"                        % circeVersion,
  "io.circe" %% "circe-generic"                     % circeVersion,
  "io.circe" %% "circe-parser"                      % circeVersion,
  "io.circe" %% "circe-optics"                      % circeVersion,

  "io.monix" %% "monix"           % monixVersion,
  "io.monix" %% "monix-execution" % monixVersion,
  "io.monix" %% "monix-eval"      % monixVersion,

  "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.3.3",

  "com.typesafe.slick"         %% "slick"        % "3.3.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "com.h2database" % "h2"            % h2Version,
  "org.mockito"    %  "mockito-core" % "2.27.0",
  "org.slf4j"      % "slf4j-simple" % "1.7.25",
  "org.scalatest"  %% "scalatest"    % "3.0.5",

  "org.jsoup" % "jsoup" % "1.11.3"
)

scalacOptions ++= Seq("-Ypartial-unification", "UTF-8")
