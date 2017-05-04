package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import model.{Advert, Diesel}
import play.api.libs.json.Json

/**
 * TODO docs.
 */
@Singleton
class AdvertController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def getAdvert = Action { implicit request =>
    Ok(Json.toJson(Advert(1, "Titel",Diesel, 500, true, None, None))).as(JSON)
  }



}
