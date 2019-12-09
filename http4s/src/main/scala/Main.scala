import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.server.blaze.BlazeServerBuilder
import controllers.Http4sController._
import cats.implicits._


object Main extends IOApp {
	
	def run(args: List[String]): IO[ExitCode] =
		BlazeServerBuilder[IO]
			.bindHttp(9000, "localhost")
			.withHttpApp(http4sRoutes)
			.serve
			.compile
			.drain
			.as(ExitCode.Success)
	
	
}
