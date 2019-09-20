package com.database

import java.util.UUID
import com.model.DangerousGood
import slick.jdbc.H2Profile.api._

class DangerousGoods(tag: Tag) extends Table[DangerousGood](tag, "Dangerous_Goods") {
  def uuid = column[UUID]("UUID", O.PrimaryKey, O.Default(UUID.randomUUID()))
  def name = column[String]("name")
  def number = column[String]("number")
  def code = column[String]("code")
  def description = column[String]("description")
  def * = (uuid.?, name, number, code, description) <> (DangerousGood.tupled, DangerousGood.unapply)
}
