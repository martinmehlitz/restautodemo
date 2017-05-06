package model

import java.util.UUID

/**
  * This is a simple version of a persistence implementation for Adverts.
  */
object InMemoryAdvertPersistance extends AdvertPersistence {

  var adverts: Map[String, Advert] = Map("bla" -> Advert("bla", "title", Diesel, 500, true, None, None),
    "blub" -> Advert("blub", "title2", Diesel, 5000, true, None, None))

  //TODO sorting
  override def retrieveAll(): Seq[Advert] = {
    adverts.values.toSeq
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