package model

import play.api.libs.json.Json

case class Limit(
    limit:Int,
    offset:Int)

object Limit {
  implicit val Limit = Json.format[Limit]
}