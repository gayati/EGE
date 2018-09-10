package model

import play.api.libs.json.Json

case class ResultDto(result:Int)

object ResultDto{
  implicit val ResultDto = Json.format[ResultDto]
}

case class UserDto(
    name:String,
    email:String)

object UserDto{
  implicit val UserDto = Json.format[UserDto]
}