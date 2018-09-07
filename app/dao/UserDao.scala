package dao

import javax.inject.Singleton
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import model.User
import play.modules.reactivemongo._
import reactivemongo.api._
import reactivemongo.play.json.collection.JSONCollection
import play.api.libs.json.Json
import reactivemongo.play.json._
import reactivemongo.bson.BSONDocument
import javax.swing.text.StyledEditorKit.BoldAction
import reactivemongo.api.commands.WriteResult

@Singleton
class UserDao @Inject() (val reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext) extends IUserDao {

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("user"))

  override def addUser(user: User): Future[User] = {
    collection.flatMap(_.insert(user))
    Future.successful(user)
  }

  override def getUserById(userId: Int): Future[Option[User]] = {
    val query = BSONDocument("id" -> userId)
    collection.flatMap(_.find(query).one[User]).map { user =>
      user
    }
  }

  override def updateUser(user: User): Future[Boolean] = {
    if (user.email != "") {
      collection.flatMap(_.update(BSONDocument("id" -> user.id), BSONDocument("$set" -> BSONDocument("email" -> user.email))))
      Future { true }
    } else {
      Future { false }
    }
  }
  
  override def deleteUser(userId: Int): Future[WriteResult] = {
    collection.flatMap(_.remove(BSONDocument("id" -> userId))) map { future => 
     println("future     " + future)
     future 
    }
  }

  //  override def getAllUsers(): Future[List[User]] = {
  //    collection.flatMap(_.find(BSONDocument(), BSONDocument("_id" -> 0)
  //  }

  
} 