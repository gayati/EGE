package model
import play.api.libs.json.Json


case class User(id:Int,name:String,email:String)

object User {
  implicit  val User = Json.format[User]
}