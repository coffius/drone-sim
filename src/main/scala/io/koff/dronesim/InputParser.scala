package io.koff.dronesim

import java.io.InputStream

import io.koff.dronesim.model.{Direction, DroneCommand}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Try

/**
  * Parser of input text data for drone simulation
  * @see parse(...)
  */
object InputParser {

  /**
    * Parses the input stream and returns [[SimulationInput]] if everything is ok
    * @param input input stream with data
    * @return Try(SimulationInput)
    */
  def parse(input: InputStream): Try[SimulationInput] = {
    // try to parse input
    Try(parseInternal(input))
  }

  /**
    * Tries to parse input and returns [[SimulationInput]] if everything is ok or throws [[ParseException]] if something is wrong with data
    * @param input input stream with data
    * @return new object of SimulationInput
    * @throws ParseException if something is wrong with data
    */
  private def parseInternal(input: InputStream): SimulationInput = {
    val linesIter = Source.fromInputStream(input).getLines()
    val cornerLine = readLineFromIter(linesIter, "read coordinates of a point")
    val upperRightCorner = parsePoint(cornerLine)

    val inputBuf = new ArrayBuffer[DroneInput]
    while (linesIter.hasNext) {
      val startStateLine = readLineFromIter(linesIter, "read a line with the start state")
      val startState = parseStartState(startStateLine)
      val commandsLine = readLineFromIter(linesIter, "read drone commands")
      val commands = parseCommands(commandsLine)
      inputBuf += DroneInput(startState, commands)
    }
    SimulationInput(upperRightCorner, inputBuf)
  }

  /**
    * Tries to parse the string into Point()
    * @param line input string
    * @return Point() with coordinates
    * @throws ParseException if it is impossible to parse the input string
    */
  def parsePoint(line: String): Point = {
    val parts = line.trim.split(' ')
    if(parts.length != 2) {
      throw new ParseException("read point", line)
    } else if(!parts.forall(_.forall(Character.isDigit))){
      throw new ParseException("read point", line)
    } else {
      val x = parts(0).toInt
      val y = parts(1).toInt
      Point(x, y)
    }
  }

  /**
    * Tries to parse the input line as DroneStartState
    * @param line the input line
    * @return DroneStartState
    * @throws ParseException if it is impossible to parse the input string
    */
  def parseStartState(line: String): DroneStartState = {
    val parts = line.trim.split(' ')
    if(parts.length != 3) {
      throw new ParseException("read the start state", line)
    } else if(!parts(0).forall(Character.isDigit) || !parts(1).forall(Character.isDigit)){
      throw new ParseException("read the start position", line)
    } else if(!Direction.isDirection(parts(2))) {
      throw new ParseException("read the start direction", line)
    } else {
      val x = parts(0).toInt
      val y = parts(1).toInt
      val dir = Direction.toDirection(parts(2))
      DroneStartState(Point(x, y), dir)
    }
  }

  /**
    * Tries to read the string line from iterator and throws ParseException if it is impossible
    * @param iter string iterator
    * @param action name of the current action
    * @return the next string
    * @throws ParseException if there are no available strings
    */
  private def readLineFromIter(iter: Iterator[String], action: String): String = {
    if(iter.hasNext) {
      iter.next()
    } else {
      throw new ParseException(action, "<empty line>")
    }
  }

  /**
    * Tries to parse the input string as a list of drone commands
    * @param line the input string
    * @return seq of drone commands
    * @throws ParseException if there is an unknown character
    */
  def parseCommands(line: String): Seq[DroneCommand] = {
    line.map{ c =>
      if(DroneCommand.isCommand(c.toString)) {
        DroneCommand.toCommand(c.toString)
      } else {
        throw new ParseException(s"read unknown character[$c]", line)
      }
    }
  }

  case class SimulationInput(upperRightCorner: Point, droneInputs: Seq[DroneInput])
  case class DroneStartState(startPos: Point, startDir: Direction)
  case class DroneInput(startState:DroneStartState, commands: Seq[DroneCommand])
}

class ParseException(action: String, line: String) extends Exception(s"Error during action[$action] in line[$line]")