package model

import java.util.UUID

/**
  * This is a simple version of a persistence implementation for Adverts.
  *
  * TODO synchronization for creation of ids
  */
object InMemoryAdvertPersistance extends AdvertPersistence {

  var adverts: Map[String, Advert] = Map()

  //TODO sorting
  override def retrieveAllAdverts(): Seq[Advert] = {
    adverts.values.toSeq
  }

  override def retrieveAdvert(id: String): Option[Advert] = adverts.get(id)

  override def createAdvert(advert: Advert): Advert = {
    val result = advert.copy(id = UUID.randomUUID.toString)
    adverts = adverts + (result.id -> result)
    result
  }


  override def updateAdvert(advert: Advert): Advert = {
    adverts = adverts + (advert.id -> advert)
    advert
  }

  override def deleteAdvert(id: String): Boolean = {
    val old = retrieveAdvert(id)
    old match {
      case None => false
      case Some(a) =>
        adverts = adverts - id
        true
    }
  }
}
