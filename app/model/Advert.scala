package model


import play.api.libs.json._

sealed trait FuelType
 case object Gasoline extends FuelType
 case object Diesel extends FuelType

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
        "new" -> advert.isNew
      )
      case  false => Json.obj(
        "id" -> advert.id,
        "title" -> advert.title,
        "fuel"-> advert.fuel.toString,
        "price" -> advert.price,
        "new" -> advert.isNew,
        "mileage" -> advert.mileage.get,
        "firstRegistration" -> advert.firstRegistration.get
      )
    }
  }


}