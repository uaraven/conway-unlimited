import play.api.libs.json._

/**
 * TODO: Write JavaDoc
 */
package object models {

  implicit val cellsWrites = new Writes[Cell] {
    override def writes(o: Cell): JsValue = Json.obj("x" -> JsNumber(o.x), "y" -> JsNumber(o.y))
  }

  implicit val gameOfLifeWrites = new Writes[GameOfLife] {
    override def writes(o: GameOfLife): JsValue = Json.obj(
      "generation" -> o.getGenerationNumber,
      "history" -> o.getGenerationsAlive,
      "cells" -> o.getField.cells
    )
  }

  def jsonize(gol: GameOfLife) = Json.toJson(gol)

}
