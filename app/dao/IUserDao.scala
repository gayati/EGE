package dao

import com.google.inject.ImplementedBy
import model.User
import scala.concurrent.Future
import reactivemongo.api.Cursor
import reactivemongo.api.commands.WriteResult

@ImplementedBy(classOf[UserDao])
trait IUserDao {
  def addUser(user: User): Future[User]
  def getUserById(userId: Int): Future[Option[User]]
  def updateUser(user:User): Future[Boolean]
  def updateUserName(user:User): Future[Boolean]
  def getAllUsers(limit:Int,offset:Int):Future[List[User]]
  def deleteUser(userId:Int):Future[WriteResult]
  
}  