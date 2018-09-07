package controllers

import javax.inject.Singleton
import javax.inject.Inject
import play.api.mvc.ControllerComponents
import play.api.mvc.AbstractController
import play.api.mvc.Request
import play.api.mvc.AnyContent
import model.Sum
import model.ResultDto

@Singleton
class AdditionController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def add(num1: Int, num2: Int) = Action { implicit request: Request[AnyContent] =>
    var result: Int = num1 + num2;
    Ok("The addition of " + num1 + " and " + num2 + " is " + result.toString())
  }

  def add1() = Action { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      var sum: Sum = json.as[Sum]
      var result = sum.num1 + sum.num2;
      println("result......." + result)
      Ok(result.toString())
    }.getOrElse({
      BadRequest("Error...")
    })
  }

}