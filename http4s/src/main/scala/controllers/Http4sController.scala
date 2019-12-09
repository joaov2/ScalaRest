package controllers

import cats.effect._
import growinupscala.{Id, JSON}
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import growinupscala.controllers.Controller
import services.Http4sService._
import cats.implicits._
import repositories.utils.RepMessages._
import services.Http4sService

object Http4sController extends Controller[IO[Response[IO]]] {
	
	val notFoundResponse = "Resource not found"
	
	def get(id: Id): IO[Response[IO]] = read(id).redeemWith({
		case e: Exception if e.getMessage == notFound => NotFound(notFoundResponse)
	}, Ok(_))
	
	def post(resource: JSON): IO[Response[IO]] = create(resource).flatMap(Ok(_))
	
	def put(id: Id, resource: JSON): IO[Response[IO]] = update(id, resource).redeemWith({
		case e: Exception if e.getMessage == notFound => NotFound(notFoundResponse)
	}, _ => NoContent())
	
	def delete(id: Id): IO[Response[IO]] = Http4sService.delete(id).redeemWith({
		case e: Exception if e.getMessage == notFound => NotFound(notFoundResponse)
	}, _ => NoContent())
	
	
	val http4sRoutes: HttpApp[IO] = HttpRoutes.of[IO] {
		case GET -> Root / "http4s" / id => get(id)
		
		case req @ POST -> Root / "http4s" => req.decode[JSON] {
			json => post(json)
		}
		
		case req @ PUT -> Root / "http4s" / id  => req.decode[JSON] {
			json => put(id, json)
		}
		
		case DELETE -> Root / "http4s" / id => delete(id)
	}.orNotFound
	
}
