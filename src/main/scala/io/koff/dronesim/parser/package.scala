package io.koff.dronesim

import io.koff.dronesim.model.{Direction, DroneCommand}

package object parser {
  case class SimulationInput(upperRightCorner: Point, droneInputs: Seq[DroneInput])
  case class DroneStartState(startPos: Point, startDir: Direction)
  case class DroneInput(startState:DroneStartState, commands: Seq[DroneCommand])
}
