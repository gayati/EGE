package service

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import dao.IUserDao
import javax.inject.Singleton
import model.User
import model.UserDto
import reactivemongo.api.commands.WriteResult

@Singleton
class UserService @Inject() (userDao: IUserDao)(implicit ec: ExecutionContext) extends IUserService {

  override def addUser(user: User): Future[User] = {
    userDao.addUser(user) map { userFuture =>
      userFuture
    }
  }

  override def getUserById(userId: Int): Future[Option[User]] = {
    userDao.getUserById(userId) map { getFuture =>
      getFuture
    }
  }

  override def updateUser(user: User): Future[Boolean] = {
    userDao.updateUser(user) map { updateFuture =>
      updateFuture
    }
  }
  
  override def deleteUser(userId:Int):Future[WriteResult]={
    userDao.deleteUser(userId) map { deleFuture =>
      deleFuture 
    }
  }
}