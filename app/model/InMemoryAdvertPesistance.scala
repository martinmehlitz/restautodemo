package model

import java.util.UUID

/**
  * This is a simple version of a persistence implementation for Adverts.
  */
object InMemoryAdvertPersistance extends AdvertPersistence {

  var adverts: Map[String, Advert] = Map("bla" -> Advert("bla", "title", Diesel, 500, true, None, None),
    "blub" -> Advert("aaaa", "Automobile adverts are awesome", Gasoline, 100, true, None, None))

  private def sortById(a1: Advert, a2: Advert): Boolean = {
    a1.id.compareTo(a2.id) < 0
  }
  private def sortByPrice(a1: Advert, a2: Advert): Boolean = {
    a1.price.compareTo(a2.price) < 0
  }

  //TODO sorting
  override def retrieveAll(sort: String, sortAsc: Boolean): Seq[Advert] = {
    var result = adverts.values.toSeq
    result = sort match {
      case "price" => result.sortWith(sortByPrice)
      case _ => result.sortWith(sortById)
    }
    if (!sortAsc) result.reverse else result
  }

  override def retrieve(id: String): Option[Advert] = adverts.get(id)

  override def create(advert: Advert): Advert = {
    if (!advert.isNew && (advert.firstRegistration.isEmpty || advert.mileage.isEmpty))
      throw new Error("Invalid advert. Used cars need to define the date of the first registration and the current mileage")
    val result = advert.copy(id = UUID.randomUUID.toString)
    adverts = adverts + (result.id -> result)
    result
  }


  override def update(advert: Advert): Advert = {
    adverts = adverts + (advert.id -> advert)
    advert
  }

  override def delete(id: String): Boolean = {
    val old = retrieve(id)
    old match {
      case None => false
      case Some(a) =>
        adverts = adverts - id
        true
    }
  }
}