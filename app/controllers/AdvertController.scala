package controllers

import javax.inject._

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import model.{Advert, Diesel, InMemoryAdvertPersistance}
import play.api.libs.json.{JsArray, Json}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.util.{Failure, Success, Try}

/**
 * TODO docs.
 */
@Singleton
class AdvertController @Inject() extends Controller {

  val advertPersistance = InMemoryAdvertPersistance //TODO inject

  /**
    * TOdo
    */
  def retrieveAll = Action { implicit request =>
    val result = new JsArray(advertPersistance.retrieveAll().map(a => Json.toJson(a)))
    Ok(result).as(JSON)
  }

  /**
    * TOdo
    */
  def retrieve(id: String) = Action { implicit request =>
    val result = advertPersistance.retrieve(id)
    result match {
      case Some(a) => Ok(Json.toJson(a)).as(JSON)
      case None => NotFound("Invalid Advert Id")
    }

  }

  /**
    * TOdo
    */
  def create() = Action(validateJson[Advert]) { request =>
    Try {
      val advert = request.body
      advertPersistance.create(advert)
    } match {
      case Success(savedAdvert) => Ok(Json.toJson(savedAdvert)).as(JSON)
      case Failure(e) => InternalServerError(e.getMessage)
    }
  }




  // This helper parses and validates JSON using the implicit `placeReads`
  // above, returning errors if the parsed json fails validation.
  def validateJson[A : Reads] = BodyParsers.parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )


}
