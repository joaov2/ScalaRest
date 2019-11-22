package repositories

import java.util.UUID.randomUUID

import growinupscala.{Id, JSON}
import growinupscala.repositories.Repository
import slick.jdbc.MySQLProfile.backend.Database
import slick.jdbc.MySQLProfile.api._
import utils.RepMessages._

import scala.concurrent.{ExecutionContext, Future}

class SlickRep (implicit executionContext: ExecutionContext) extends Repository[JSON] {
	
	val db = Database.forConfig("db")
	
	def create(resource: JSON): Future[Id] = {
		val id: Id = randomUUID().toString
		db.run(ScalaRestTable.all.+=(ScalaRestRow(id, resource)).map(_ => id))
	}
	
	def read(id: Id): Future[JSON] = db.run(
		ScalaRestTable.all.filter(_.id === id).map(_.json).result.headOption.map{
			case Some(json) => json
			case None => notFound
		})
	
	def update(id: Id, resource: JSON): Future[Unit] = db.run(
		ScalaRestTable.all.filter(_.id === id).map(_.json).update(resource).map{
		case 0 => throw new Exception(notFound)
		case _ => ()
		})
	
	def delete(id: Id): Future[Unit] = db.run(
		ScalaRestTable.all.filter(_.id === id).delete.map{
			case 0 => throw new Exception(notFound)
			case _ => ()
		})
	
	
}
