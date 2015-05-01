package models

import models.Generation._
import play.api.libs.json.Format

import scala.util.Random

class GameOfLife(val clusters: Int, val clusterSize: Int) {
  val dc = math.sqrt(clusterSize).toInt
  private var field = new Generation(seedCells)

  private var generationNumber = 0
  private var generationAlive = List(field.cells.size)

  def advance(): Unit = {
    generationNumber += 1
    field = field.next
    generationAlive = generationAlive :+ field.cells.size
  }

  private def randomCoord = Random.nextInt(3000) - 1500

  private def randomCell(center: Cell) = center + new Cell(Random.nextInt(dc * 2) - dc, Random.nextInt(dc * 2) - dc)

  private def seedCells = (for (i <- 0 until clusters; cell <- generateCellsInCluster(randomCluster)) yield cell).toSet

  private def randomCluster = new Cell(randomCoord, randomCoord)

  private def generateCellsInCluster(clusterCell: Cell) = {
    (for (i <- 0 until clusterSize) yield randomCell(clusterCell)).toSet
  }

  def getField = field

  def getGenerationNumber = generationNumber

  def getGenerationsAlive = generationAlive

}

case class Cell(x: Int, y: Int) {
  def +(other: Cell) = new Cell(x + other.x, y + other.y)
}

class Generation(val cells: Set[Cell]) {

  private def isValid(x: Int, y: Int, cell: Cell): Boolean = cell.x + x > Integer.MIN_VALUE &&
    cell.x + x < Integer.MAX_VALUE && cell.y + y > Integer.MIN_VALUE && cell.y + y < Integer.MAX_VALUE &&
    cell.x != x && cell.y != y

  private def neighborhood(cell: Cell) = for (dx <- offsets;
                                              dy <- offsets if isValid(dx, dy, cell))
    yield new Cell(cell.x + dx, cell.y + dy)

  private def aliveNeighbors(cell: Cell) = neighborhood(cell) filter cells.contains

  private def deadNeighbors(cell: Cell) = neighborhood(cell).filter(!cells.contains(_))

  private def potentiallyAlive = for (cell <- cells; dead <- deadNeighbors(cell)) yield dead

  private def numberOfAliveNeighbors(cell: Cell) = aliveNeighbors(cell).size

  private def willLive(cell: Cell) = suitableNeighborsCount contains numberOfAliveNeighbors(cell)

  private def willBorn(cell: Cell) = numberOfAliveNeighbors(cell) == 3

  private def survivors = for (cell <- cells if willLive(cell)) yield cell

  private def newborn = for (cell <- potentiallyAlive if willBorn(cell)) yield cell

  def next = new Generation(survivors ++ newborn)
}

object Generation {
  private val offsets = List(-1, 0, 1)
  private val suitableNeighborsCount = Set[Int](2, 3)
}
