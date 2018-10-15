package model
import play.api.libs.json.Json
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONObjectID


case class User(
    id:Int,
    name:Option[String],
    email:Option[String])

object User {
  implicit val User = Json.format[User]
}
  