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
import model.Limit
import java.nio.file.Paths
import com.sksamuel.scrimage
import com.sksamuel.scrimage.nio.JpegWriter
import com.sksamuel.scrimage.Image
import java.io.File
import java.io.InputStream
import org.apache.tika.exception.TikaException
import org.apache.tika.metadata.Metadata
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.sax.WriteOutContentHandler

@Singleton
class UserController @Inject() (cc: ControllerComponents, configuration: play.api.Configuration)(userService: IUserService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

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
      getFuture match {
        case Some(user) => Ok(Json.obj("SUCCESS" -> 1, "USER" -> getFuture.get))
        case None => Ok(Json.obj("Error" -> "User is not present"))
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

  def getAllUser() = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      val limit = json.as[Limit]
      userService.getAllUsers(limit) map { getUserFuture =>
        Ok(Json.obj("Result" -> getUserFuture))
      }
    }.getOrElse(Future {
      BadRequest("InvalidJson")
    })
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { picture =>
      println("picture.............." + picture)
      val filename = Paths.get(picture.filename).getFileName
      println("fileaname............." + filename)
      val filePath = configuration.underlying.getString("file.path")
      val file1 = Paths.get(s"filePath$filename")
      picture.ref.moveTo(file1, replace = true)
      Ok("http://localhost:9000/serveUploadedFiles/" + filename)
    }.getOrElse {
      BadRequest("Missing File")
    }
  }

  def serveUploadedFiles(file: String) = Action {
    print(file + "ServeUploadFile...............")
    Ok.sendFile(
      content = new java.io.File("/home/ege/Documents/DemoPractice/demo_app/images/" + file),
      fileName = _ => file,
      inline = false)
  }

  def extract() = Action { implicit request: Request[AnyContent] =>
    var istream: InputStream = null
    //  try {
    istream = new FileInputStream(new File("/home/ege/Downloads/Pardesiya - Songs.pk - 128Kbps.mp3"))
    val handler = new WriteOutContentHandler(-1)
    val metadata = new Metadata()
    val parser = new Mp3Parser()
    val ctx = new ParseContext()
    parser.parse(istream, handler, metadata, ctx)  
    System.out.println("Contents of the document:" + handler.toString());
    System.out.println("Metadata of the document:");
    var metadataNames = metadata.names();
    for (name <- metadataNames) {
      System.out.println(name + ": " + metadata.get(name));
    }
    System.out.println("MetaData: " + metadata);
    System.out.println("Title: " + metadata.get("title"));
    System.out.println("Artists: " + metadata.get("xmpDM:artist"));
    System.out.println("Genre: " + metadata.get("xmpDM:album"));
      Ok(Json.obj(  
          "Album: " -> metadata.get("xmpDM:album"),
          "Track Number: " -> metadata.get("xmpDM:trackNumber"),
          "Release Date: " -> metadata.get("xmpDM:releaseDate"),
          "Version: " -> metadata.get("version"),
          "Creator: " -> metadata.get("creator"),
          "Title: " -> metadata.get("title"),
          "Artists: " -> metadata.get("xmpDM:artist"),
          "Genre: " -> metadata.get("xmpDM:genre")
           ))

    
    //    } catch {
    //      case e: TikaException => Map[DocPart.Value,String](
    //        (DocPart.Error, e.getMessage()))
    //    } finally {
    //      IOUtils.closeQuietly(istream)
    //    }
  }

}