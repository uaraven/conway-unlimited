import models.GameOfLife
import models._

val field = new GameOfLife(1, 10)
field.getGenerationNumber
field.getGenerationsAlive

jsonize(field)

field.advance()

field.getField.cells
field.getGenerationNumber
field.getGenerationsAlive


field.advance()

field.getField.cells
field.getGenerationNumber
field.getGenerationsAlive


field.advance()

field.getField.cells
field.getGenerationNumber
field.getGenerationsAlive



field.advance()

field.getField.cells
field.getGenerationNumber
field.getGenerationsAlive
