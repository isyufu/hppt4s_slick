package com

import cats.effect._
import cats.implicits._
import com.service.{Loader, TestService}
import org.http4s.server.blaze.BlazeServerBuilder

object Boot extends IOApp  {

  def run(args: List[String]): IO[ExitCode] = {
    database.init()
    Loader.awaitParsePage()
    println(s"size: ${database.count()}")
    IO().as(ExitCode.Success)
//    BlazeServerBuilder[IO]
//      .bindHttp(8080, "localhost")
//      .withHttpApp(TestService.serviceRoutes)
//      .serve
//      .compile
//      .drain
//      .as(ExitCode.Success)
  }
}
