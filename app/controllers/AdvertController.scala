package controllers

import javax.inject._

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import model.{Advert, AdvertPersistence, InMemoryAdvertPersistance}
import play.api.libs.json.{JsArray, Json}
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

/**
 * TODO docs.
 */
@Singleton
class AdvertController @Inject() extends Controller {

  val advertPersistance: AdvertPersistence = InMemoryAdvertPersistance //TODO inject, dont hardwire

  /**
    * TOdo
    */
  def retrieveAll(sortOption: Option[String], sortAscOption: Option[Boolean]) = Action { implicit request =>
    Try {
      val sort = if (sortOption.isDefined) sortOption.get else "id"
      val sortAsc = if (sortAscOption.isDefined) sortAscOption.get else true
      new JsArray(advertPersistance.retrieveAll(sort, sortAsc).map(a => Json.toJson(a)))
    } match {
      case Success(result) => Ok(result).as(JSON)
      case Failure(e) => InternalServerError(e.getMessage)
    }
  }

  /**
    * TOdo
    */
  def retrieve(id: String) = Action { implicit request =>
    Try {
      advertPersistance.retrieve(id)
    } match {
      case Success(result) =>
        result match {
          case Some(a) => Ok(Json.toJson(a)).as(JSON)
          case None => NotFound("Invalid Advert Id")
        }
      case Failure(e) => InternalServerError(e.getMessage)
    }
  }

  /**
    * TOdo
    */
  def delete(id: String) = Action { implicit request =>
    Try {
      advertPersistance.delete(id)
    } match {
      case Success(result) =>
        result match {
          case true => Ok(s"Successfully deleted $id.")
          case false => NotFound("Invalid Advert Id")
        }
      case Failure(e) => InternalServerError(e.getMessage)
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

  /**
    * TOdo
    */
  def update() = Action(validateJson[Advert]) { request =>
    Try {
      val advert = request.body
      advertPersistance.update(advert)
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