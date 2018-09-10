package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import model.User
import service.IUserService
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.libs.json.Json
import model.UserDto
import dao.UserDao

@Singleton
class UserController @Inject() (cc: ControllerComponents)(userService: IUserService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def addUser() = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      var user: User = json.as[User]
      userService.addUser(user).map { addUserSuccess =>
        Ok(Json.obj("SUCCESS" -> 1, "USER" -> addUserSuccess))
      }
    }.getOrElse(Future {
      BadRequest("User has made a bad request")
    })
  }

  def getUserById(userId: Int) = Action.async { implicit request: Request[AnyContent] =>
    userService.getUserById(userId).map({ getFuture =>
      getFuture  match {
        case Some(user) => Ok(Json.obj("SUCCESS" -> 1, "USER" -> getFuture.get))
        case None   => Ok(Json.obj("Error" -> "User is not present"))

      }
    })
  }

  def updateUser() = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      val user: User = json.as[User]
      userService.updateUser(user) map { updateFuture =>
        Ok(Json.obj("Result" -> updateFuture))
      }
    }.getOrElse(Future {
      BadRequest("InvalidJson")
    })
  }
  
  def updateUserName() = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      val user: User = json.as[User]
      userService.updateUser(user) map { updateFuture =>
        Ok(Json.obj("Result" -> updateFuture))
      }
    }.getOrElse(Future {
      BadRequest("InvalidJson")
    })
  }

  def deleteUser(userId: Int) = Action.async { implicit request: Request[AnyContent] =>
    userService.deleteUser(userId) map { deleteFuture =>
      Ok(Json.obj("Result" -> deleteFuture.toString()))
     
    }
  }
  
  def getAllUser() = Action.async{ implicit request: Request[AnyContent] =>
    userService.getAllUsers() map { getUserFuture =>
      Ok(Json.obj("Result" -> getUserFuture))
      
    }
    
  }

}