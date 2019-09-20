package com.service
import java.util.UUID

import com.database.DangerousGoodsRepos
import com.model.{DangerousGood, _}
import monix.execution.Scheduler
import monix.execution.Scheduler.Implicits.global

import concurrent.duration._
import monix.execution.schedulers._
import monix.eval.Task
import monix.execution.CancelableFuture
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}

import scala.concurrent.Await
import scala.util.Try
//import concurrent.duration._
import scala.collection.JavaConverters._

object Loader {
  lazy val executorService = scala.concurrent.ExecutionContext.Implicits.global
  lazy val scheduler = Scheduler(executorService)

  val urlSearch = "https://www.pogt.ru/search.html"
  val urlRoot = "https://www.pogt.ru/"

  def runScheduler(): Unit = {
    scheduler.scheduleWithFixedDelay(3.seconds, 5.seconds) {
      println("Fixed delay task")
    }
  }

  val x = Task { println("ss"); ""}

  def tryOrEmpty[T](default:T)(arr:Seq[T])(i:Int):T = Try{arr(i)}.getOrElse(default)

  val loadBody = (url:String) => Task { Jsoup.connect(url).get() }

  def parseTrAndInsertDg(tr:Element) = {
    val tds = tr.getElementsByTag("td")
    val columns = tds.eachText()

    def columnOrEmpty(i:Int) = tryOrEmpty("")(columns.asScala)(i)
    val descrUrl = tds.attr("href")

    val dg = DangerousGood(Some(UUID.randomUUID()), columnOrEmpty(1), columnOrEmpty(0), columnOrEmpty(3), "")

    loadBody(urlRoot+descrUrl).map{ doc =>
      doc.body().getElementsByClass("oon_discription")
        .eachText()
        .asScala
        .flatten
        .mkString(" ")
    }
      .map(d => dg.copy(description = d))
      .flatMap(dg => Task.fromFuture(DangerousGoodsRepos.insertF(dg)))
  }

  def parsePage() = {
    loadBody(urlSearch).map{ doc =>
      doc.body().select(".danger-class-1 > tbody:last-child > tr")
    }.map(_.iterator().asScala.map(parseTrAndInsertDg).toList)
      .flatMap(Task.sequence(_))
      .runToFuture
  }

  def awaitParsePage() = {
    val x = Await.result(parsePage(), Duration.Inf)
    println(s"cancel parse page: ${x.sum}")
  }
}
