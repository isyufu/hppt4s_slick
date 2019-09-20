package com

import java.util.UUID

package object model {

  case class DangerousGood(uuid:Option[UUID],
                           name: String,
                           number:String,
                           code: String,
                           description:String)

}
