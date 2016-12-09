package io.koff.dronesim

/**
  * Platform where drones can move
  */
class Platform(upperRightCorner: Point, bottomLeftCorner: Point = Point(0,0)) {
  /**
    * Simulates movement of the drone using the list of commands from the start position and direction
    * @param startPos start position of the drone
    * @param startDir start direction of the drone
    * @param commands list of commands for the drone
    * @return finish state of the drone
    */
  def simulateDrone(startPos: Point, startDir: Direction, commands: Seq[DroneCommand]): SimResult = ???
}

object Platform {
  /**
    * A droid has left the platform
    * @param pos last droid position
    * @param direction last droid direction
    */
  case class OutOfBoundaries(pos: Point, direction: Direction) extends ErrorResult

  /**
    * Invalid start position of droid
    * @param pos start position
    */
  case class InvalidInitPosition(pos: Point) extends ErrorResult
}