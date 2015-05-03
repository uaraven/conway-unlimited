package controllers

import models.GameOfLife
import play.api.mvc.Controller
import models._

object Game extends Controller {

  val CLUSTER_SIZE: Int = 150
  val CLUSTERS = 40

  val gameOfLife = new GameOfLife(CLUSTERS, CLUSTER_SIZE)

  def game = jsonize(gameOfLife)
}
