package service

import com.google.inject.ImplementedBy
import scala.concurrent.Future
import model.User
import model.UserDto
import reactivemongo.api.commands.WriteResult

@ImplementedBy(classOf[UserService])
trait IUserService {
  def addUser(user:User):Future[User]
  def getUserById(userId:Int):Future[Option[User]]
  def updateUser(user:User):Future[Boolean]
  def deleteUser(userId:Int):Future[WriteResult]
}