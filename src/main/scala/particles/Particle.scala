package particles

import core.{Agent, Environment}

/** Agent for a particles room simulation.
  *
  * @constructor create a new particle.
  * @param environment environment of the particle
  * @param posX abscissa position of the particle in the environment
  * @param posY ordinate position of the particle in the environment
  * @param dirX abscissa direction of the particle in the environment
  * @param dirY ordinate direction of the particle in the environment
  *
  * @author Quentin Baert
  */
class Particle(
  override val environment: Environment,
  var posX: Int,
  var posY: Int,
  var dirX: Int,
  var dirY: Int
) extends Agent {

  // Update positions of the particle
  private def updatePos(newX: Int, newY: Int): Unit = {
    this.posX = newX
    this.posY = newY
  }

  // Update directions of the particle
  private def updateDir(newX: Int, newY: Int): Unit = {
    this.dirX = newX
    this.dirY = newY
  }

  override def decide: Unit = {
    val newX = this.posX + this.dirX
    val newY = this.posY + this.dirY

    if (this.environment.isOut(newX, newY)) {
      val (newDirX, newDirY) =
        if (newX < 0 || newX >= this.environment.width)
          (this.dirX * (-1), this.dirY)
        else
          (this.dirX, this.dirY * (-1))

      this.updateDir(newDirX, newDirY)
    } else if (this.environment.isFull(newX, newY)) {
      val other = this.environment.getAgentAt(newX, newY)

      this.updateDir(this.dirX * other.dirX, this.dirY * other.dirY)
    } else {
      this.updatePos(newX, newY)
      this.environment.emptyCell(this.posX, this.posY)
      this.environment.addAgent(this)
    }
  }

}