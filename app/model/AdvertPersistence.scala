package model

trait AdvertPersistence {
  def retrieveAllAdverts(): Seq[Advert]//TODO sorting
  def retrieveAdvert(id: String): Advert
  def createAdvert(advert: Advert): Advert
  def updateAdvert(advert: Advert): Advert
  def deleteAdvert(id: String): Boolean
}