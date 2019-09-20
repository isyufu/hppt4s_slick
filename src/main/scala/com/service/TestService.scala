package com.service

import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe._
import org.http4s._
import io.circe._
import io.circe.syntax._

import com.database._
import com.model.Encoders._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object TestService {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  //  def removeNull(x: Json): String = x.printWith(Printer.spaces2.copy(dropNullValues = true))
  //  def jsonOK(s1: Json): IO[Response[IO]] = Ok(removeNull(s1)).map(_.putHeaders(Header("Content-Type", "application/json")))

  def ioFromFuture[A](f:Future[A]):IO[A] = IO.fromFuture(IO{f})
  def okJson(f:Future[Json]) = Ok(ioFromFuture(f)).map(_.putHeaders(Header("Content-Type", "application/json")))

  val io = Ok(IO {
    println("I run when the IO is run.")
    "Mission accomplished!"
  })

  val ioFuture = Ok(IO.fromFuture(IO(Future {
    println("I run when the future is constructed.")
    "Greetings from the future!"
  })))

  val dbAll = okJson(DangerousGoodsRepos.allF().map(_.asJson))

  val serviceRoutes = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name => Ok(s"Hello, $name.")
    case GET -> Root / "io"  => io
    case GET -> Root / "ioFuture"  => ioFuture
    case GET -> Root / "all"  => dbAll
  }.orNotFound

}

