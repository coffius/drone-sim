package io.koff.dronesim

import java.io.FileInputStream

import io.koff.dronesim.model.Platform
import io.koff.dronesim.parser.InputParser

import scala.util.{Failure, Success}

/**
  * Main class
  */
object Main {
  val filename = "example_data.txt"

  def main(args: Array[String]): Unit = {
    val inputStream = new FileInputStream(filename)
    InputParser.parse(inputStream) match {
      case Success(input) =>
        val platform = new Platform(input.upperRightCorner)
        input.droneInputs.foreach { input =>
          platform.simulateDrone(input.startState.startPos, input.startState.startDir, input.commands) match {
            case Right(drone) => println(s"${drone.pos.x} ${drone.pos.y} ${drone.direction.inputName}")
            case Left(err) => println("simulation error: $err")
          }
        }
      // don't think that it is worth to add a logger library only for one error output
      case Failure(err) => println(s"parsing error: $err")
    }
  }
}
