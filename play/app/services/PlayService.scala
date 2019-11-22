package services

import growinupscala.{Id, JSON}
import growinupscala.services.Service
import javax.inject.Inject
import repositories.SlickRep

import scala.concurrent._

class PlayService @Inject() (implicit val ec: ExecutionContext) extends Service[JSON] {
	
	val rep = new SlickRep()(ec)
	
	def create(resource: JSON): Future[Id] = rep.create(resource)
	
	
	def read(id: Id): Future[JSON] = rep.read(id)
	
	def update(id: Id, resource: JSON): Future[Unit] = rep.update(id, resource)
	
	def delete(id: Id): Future[Unit] = rep.delete(id)
	
	
}
