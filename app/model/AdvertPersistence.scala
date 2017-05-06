package model

trait AdvertPersistence {
  def retrieveAll(): Seq[Advert]//TODO sorting
  def retrieve(id: String): Option[Advert]
  def create(advert: Advert): Advert
  def update(advert: Advert): Advert
  def delete(id: String): Boolean
}