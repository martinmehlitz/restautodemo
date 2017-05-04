package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import model.{Advert, Diesel, InMemoryAdvertPersistance}
import play.api.libs.json.Json

/**
 * TODO docs.
 */
@Singleton
class AdvertController @Inject() extends Controller {

  /**
   * TOdo
   */
  def getAllAdverts = Action { implicit request =>
    Ok(InMemoryAdvertPersistance.retrieveAllAdverts().map(a => Json.toJson(a))).as(JSON)
  }



}
