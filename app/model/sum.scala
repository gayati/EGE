package model

import play.api.libs.json.Json

case class Sum(
    num1:Int,
    num2:Int)

object Sum{
  implicit val sum = Json.format[Sum]
}