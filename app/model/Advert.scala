package model


import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.util.{Failure, Success, Try}


sealed trait FuelType
 case object Gasoline extends FuelType
 case object Diesel extends FuelType
object FuelType {
  def apply(s: String) = {
    s match {
      case "Gasoline" => Gasoline
      case "Diesel" => Diesel
      case _ => throw new Error("$s is not a valid FuelType")
    }
  }

  implicit val fuelTypeReads = new Reads[FuelType] {
    def reads(json: JsValue): JsResult[FuelType] = json match {
      case JsString(v) => Try{apply(v)} match {
        case Success(ft) => JsSuccess(ft)
        case Failure(e) => JsError(e.getMessage)
      }
      case _ => JsError("String value expected for FuelType")
    }
  }
}

case class Advert(id: String, title: String, fuel: FuelType, price: Int, isNew: Boolean, mileage: Option[Int],
                  firstRegistration: Option[String])

object Advert {

  implicit val advertWrites = new Writes[Advert] {
    def writes(advert: Advert) = advert.isNew match {
      case true => Json.obj(
        "id" -> advert.id,
        "title" -> advert.title,
        "fuel"-> advert.fuel.toString,
        "price" -> advert.price,
        "isNew" -> advert.isNew
      )
      case  false => Json.obj(
        "id" -> advert.id,
        "title" -> advert.title,
        "fuel"-> advert.fuel.toString,
        "price" -> advert.price,
        "isNew" -> advert.isNew,
        "mileage" -> advert.mileage.get,
        "firstRegistration" -> advert.firstRegistration.get
      )
    }
  }

  implicit val advertReads = Json.reads[Advert]

}