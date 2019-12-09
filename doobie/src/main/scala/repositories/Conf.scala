package repositories

import cats.effect._
import doobie._
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor.Aux

object Conf {
	implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContexts.synchronous)
	
	val xs: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
		"com.mysql.cj.jdbc.Driver",
		"jdbc:mysql://localhost/scalarest?serverTimezone=UTC",
		"root",
		"root")
}
