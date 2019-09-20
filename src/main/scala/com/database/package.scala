package com

import com.model._
import java.util.UUID

import com.database.DangerousGoods
import com.softwaremill.macwire.wire

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.basic.DatabasePublisher
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Await

package object database {

  val db:Database = Database.forConfig("h2mem1")

//  val dangerousGoods = TableQuery[DangerousGoods]

  def init(): Unit ={
    Await.result(db.run(DBIO.seq(
      DangerousGoodsRepos.schema.create//,

//      DangerousGoodsRepos.insertAll(Seq(
//        DangerousGood(Some(UUID.randomUUID()), "АММОНИЯ ПИКРАТ сухой или с массовой долей воды менее 10%", "0004", "1.1D", ""),
//        DangerousGood(Some(UUID.randomUUID()), "ПАТРОНЫ ДЛЯ ОРУЖИЯ с разрывным зарядом", "0005", "1.1F", "")
//      )),
//      DangerousGoodsRepos.all().map(println)
    )), Duration.Inf)
  }

  def count() = {
    Await.result(db.run(DangerousGoodsRepos.length.result), Duration.Inf)
  }
}
