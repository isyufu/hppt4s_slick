package com.database

import slick.lifted.TableQuery
import com.model.DangerousGood
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

object DangerousGoodsRepos extends TableQuery(new DangerousGoods(_)) {
  def insert (dg:DangerousGood): DBIO[Int] = this += dg
  def insertF (dg:DangerousGood): Future[Int] = db.run(this += dg)
  def all(): DBIO[Seq[DangerousGood]] = this.result
  def allF(): Future[Seq[DangerousGood]] = db.run(this.result)
}
