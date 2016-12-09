package io.koff.dronesim

import io.koff.dronesim.DroneCommand.{Move, TurnLeft, TurnRight}
import io.koff.dronesim.Platform.{InvalidInitPosition, OutOfBoundaries}

import scala.annotation.tailrec

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
  def simulateDrone(startPos: Point, startDir: Direction, commands: Seq[DroneCommand]): SimResult = {
    val startDroneState = Drone(startPos, startDir)
    for {
      _ <- checkBoundaries(startDroneState).right
      finalState <- simulate(startDroneState, commands).right
    } yield {
      finalState
    }
  }

  private def checkBoundaries(drone: Drone): SimResult = {
    if(!(bottomLeftCorner.x <= drone.pos.x && drone.pos.x <= upperRightCorner.x)) {
      Left(InvalidInitPosition(drone.pos))
    } else if(!(bottomLeftCorner.y <= drone.pos.y && drone.pos.y <= upperRightCorner.y)) {
      Left(InvalidInitPosition(drone.pos))
    } else {
      Right(drone)
    }
  }

  @tailrec
  private def simulate(drone: Drone, commands: Seq[DroneCommand]): SimResult = {
    commands match {
      case command :: tail => applyCommandToDrone(drone, command) match {
        case err: Left[_, _] => err
        case Right(nextState) => simulate(nextState, tail)
      }
      case Nil => Right(drone)
    }
  }

  private def applyCommandToDrone(drone: Drone, command: DroneCommand): SimResult = {
    val droneState = command match {
      case TurnLeft   => drone.copy(direction = drone.direction.left)
      case TurnRight  => drone.copy(direction = drone.direction.right)
      case Move => drone.copy(pos = drone.pos + drone.direction.moveVec)
    }

    checkBoundaries(droneState) match {
      case Left(err) => Left(OutOfBoundaries(droneState.pos, droneState.direction))
      case ok@Right(_) => ok
    }
  }
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