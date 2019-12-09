package services

import cats.effect.IO
import growinupscala.services.BetterService
import growinupscala.{Id, JSON}
import repositories.DoobieRep

object Http4sService extends BetterService[IO, JSON]{
	
	def create(resource: JSON): IO[Id] = DoobieRep.create(resource)
	
	def read(id: Id): IO[JSON] = DoobieRep.read(id)
	
	def update(id: Id, resource: JSON): IO[Unit] = DoobieRep.update(id, resource)
	
	def delete(id: Id): IO[Unit] = DoobieRep.delete(id)
	
	
}
