package controllers

import growinupscala.{Id, JSON}
import growinupscala.controllers.Controller
import javax.inject.Inject
import play.api.mvc._
import services.PlayService
import repositories.utils.RepMessages._

import scala.concurrent._


class PlayController  @Inject() (implicit val ec: ExecutionContext, cc: ControllerComponents, playService: PlayService)
	extends AbstractController(cc) with Controller[Future[Result]] {
	
	val notFoundResponse = "Resource not found"
	
	def playGet(id: Id): Action[AnyContent] = Action.async(get(id))
	
	def get(id: Id): Future[Result] = playService.read(id).map{
		case `notFound` => NotFound(notFoundResponse)
		case json => Ok(json)
	}
	
	def playPost : Action[JSON] =
		Action.async(parse.text){ implicit request: Request[JSON] =>
			post(request.body)
		}
	
	
	def post(resource: JSON): Future[Result] =
		playService.create(resource).map(Created(_))
	
	
	def playPut(id: Id) : Action[JSON] =
		Action.async(parse.text){ implicit request: Request[JSON] =>
			put(id, request.body)
		}
	
	def put(id: Id, resource: JSON): Future[Result] = playService.update(id, resource).map(_ => NoContent) recover {
	case e: Exception if e.getMessage == notFound => NotFound(notFoundResponse)
	}
	
	
	def playDelete(id: Id) : Action[AnyContent] =
		Action.async(delete(id))
		
	
	def delete(id: Id): Future[Result] = playService.delete(id).map(_ => NoContent) recover {
		case e: Exception if e.getMessage == notFound => NotFound(notFoundResponse)
	}
	
}
