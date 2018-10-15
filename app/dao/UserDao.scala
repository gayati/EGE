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
import reactivemongo.bson._
import javax.swing.text.StyledEditorKit.BoldAction
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.Cursor
import reactivemongo.api.collections.bson.BSONCollection

@Singleton
class UserDao @Inject() (val reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext) extends IUserDao
  with ReactiveMongoComponents {

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("user"))

  override def addUser(user: User): Future[User] = {
    collection.flatMap(_.insert(user))
    Future.successful(user)
  }

  override def getUserById(userId: Int): Future[Option[User]] = {
    val selector = BSONDocument("id" -> userId)
    val projection = Option.empty[BSONDocument]
    collection.flatMap(_.find(selector, projection).one[User]).map { user =>
      user
    }
  }

  override def updateUser(user: User): Future[Boolean] = {
    if (user.email != "" && user.email.nonEmpty) {
      collection.flatMap(_.update(BSONDocument("id" -> user.id), BSONDocument("$set" -> BSONDocument("email" -> user.email))))
      Future { true }
    } else {
      Future { false }
    }
  }

  override def updateUserName(user: User): Future[Boolean] = {
    if (user.name != "" && user.name.nonEmpty) {
      collection.flatMap(_.update(BSONDocument("id" -> user.id), BSONDocument("$set" -> BSONDocument("name" -> user.name))))
      Future { true }
    } else {
      Future { false }
    }
  }

  override def deleteUser(userId: Int): Future[WriteResult] = {
    collection.flatMap(_.delete().one(BSONDocument("id" -> userId))) map { future =>
      println("future     " + future)
      future
    }
  }

  override def getAllUsers(limit: Int, offset: Int): Future[List[User]] = {
    val query = BSONDocument()
    collection.flatMap(_.find(query).options(QueryOpts(offset)).cursor[User]().collect(limit, Cursor.FailOnError[List[User]]())).map { userList =>
      userList
    }
  }

}
  
  