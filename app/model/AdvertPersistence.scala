package model

trait AdvertPersistence {
  def retrieveAll(sort: String, sortAsc: Boolean): Seq[Advert]
  def retrieve(id: String): Option[Advert]
  def create(advert: Advert): Advert
  def update(advert: Advert): Advert
  def delete(id: String): Boolean
}