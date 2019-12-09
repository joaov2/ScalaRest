package repositories

import slick.jdbc.MySQLProfile.api._
import growinupscala.JSON

case class ScalaRestRow (id: String, json: JSON)

class ScalaRestTable(tag: Tag) extends Table[ScalaRestRow](tag, "rest_table") {
	
	def id = column[String]("id", O.PrimaryKey)
	def json = column[String]("json")
	
	override def * =
		(id, json) <> (ScalaRestRow.tupled, ScalaRestRow.unapply)
	
}

object ScalaRestTable {
	val all = TableQuery[ScalaRestTable]
	
}


