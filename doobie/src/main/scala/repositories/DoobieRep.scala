package repositories

import java.util.UUID.randomUUID

import cats.effect.IO
import growinupscala.{Id, JSON}
import growinupscala.repositories.BetterRepository
import doobie.implicits._
import cats.implicits._
import Conf._
import repositories.utils.RepMessages._

object DoobieRep extends BetterRepository[IO, JSON]  {
	
	def create(resource: JSON): IO[Id] = {
		val id: Id = randomUUID().toString
		
		sql"""
				 INSERT INTO rest_table (id, json)
				 VALUES ($id, $resource)
				""".update.run.map(_ => id).transact(xs)
	}
	
	def read(id: Id): IO[JSON] =
		sql"""SELECT json
				  FROM rest_table
					WHERE id = $id
		 """
			.query[String]
			.option
			.transact(xs)
	    .map{
		    case Some(json) => json
		    case None => throw new Exception(notFound)
	    }
	
	
	def update(id: Id, resource: JSON): IO[Unit] =
		sql"""
				 UPDATE rest_table
				 SET json = $resource
			   WHERE id = $id
				""".update.run.map{
			case 0 => throw new Exception(notFound)
			case _ => ()
		}.transact(xs)
	
	def delete(id: Id): IO[Unit] =
		sql"""
				 DELETE FROM rest_table
			   WHERE id = $id
				""".update.run.map{
			case 0 => throw new Exception(notFound)
			case _ => ()
		}.transact(xs)
}
