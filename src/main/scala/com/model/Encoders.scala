package com.model

import java.util.UUID
import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._

object Encoders {
  implicit val uuidEncoder = Encoder.instance[UUID](_.toString().asJson)
  implicit val uuidDecoder = Decoder.instance[UUID](_.as[String].map(UUID.fromString(_)))
  implicit val dangerousGoodEncoder = Encoder[DangerousGood]
  implicit val dangerousGoodDecoder = Decoder[DangerousGood]

  //  implicit val widgetDecoder = Encoder[Json]
  //  implicit val widgetDecoder = Decoder[Json]
}
