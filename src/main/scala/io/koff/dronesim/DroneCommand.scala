package io.koff.dronesim

/**
  * List of possible drone commands
  */
sealed abstract class DroneCommand(val inputName: Char)

object DroneCommand {
  /** Makes the drone spin 90 degrees left */
  case object TurnLeft  extends DroneCommand('L')
  /** Makes the drone spin 90 degrees right */
  case object TurnRight extends DroneCommand('R')
  /** Makes the drone move forward one grid point and maintain the same heading*/
  case object Move  extends DroneCommand('M')
}