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

//object User {
//  
//   implicit object UserReader extends BSONDocumentReader[User] {
//    def read(doc: BSONDocument): User = {
//      val id = doc.getAs[Int]("id").get
//      val name = doc.getAs[String]("name")
//      val email = doc.getAs[String]("email")
//
//      User(id, name, email)
//    }
//  }
//}