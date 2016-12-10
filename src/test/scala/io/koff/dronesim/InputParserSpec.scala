package io.koff.dronesim

import java.io.{ByteArrayInputStream, InputStream}

import io.koff.dronesim.model.Direction.{East, North}
import io.koff.dronesim.model.{DroneCommand, Point}
import io.koff.dronesim.model.DroneCommand.{Move, TurnLeft, TurnRight}
import io.koff.dronesim.parser.{DroneStartState, InputParser, ParseException}
import org.scalatest.{FreeSpec, Matchers}

import scala.language.implicitConversions
import scala.util.Success


class InputParserSpec extends FreeSpec with Matchers {

  "should parse the example input correctly" in {
    val string =
     """|5 5
        |1 2 N
        |LMLMLMLMM
        |3 3 E
        |MMRMMRMRRM""".stripMargin

    val result = InputParser.parse(string)

    result shouldBe a[Success[_]]

    val simInput = result.get
    simInput.upperRightCorner shouldBe Point(5, 5)
    simInput.droneInputs.size shouldBe 2

    val first = simInput.droneInputs.head
    first.startState shouldBe DroneStartState(Point(1, 2), North)
    first.commands shouldBe Seq(TurnLeft, Move, TurnLeft, Move, TurnLeft, Move, TurnLeft, Move, Move)

    val second = simInput.droneInputs(1)
    second.startState shouldBe DroneStartState(Point(3, 3), East)
    second.commands shouldBe Seq(Move, Move, TurnRight, Move, Move, TurnRight, Move, TurnRight, TurnRight, Move)
  }

  "should work with an empty list of commands" in {
    val string =
     """|5 5
        |1 2 N
        |
        |3 4 N
        |
        |""".stripMargin

    val result = InputParser.parse(string)

    result shouldBe a[Success[_]]

    val simInput = result.get

    val first = simInput.droneInputs.head
    first.commands shouldBe Seq.empty[DroneCommand]

    val second = simInput.droneInputs(1)
    second.commands shouldBe Seq.empty[DroneCommand]
  }

  "should throws ParseException if the line with upper right corner has a wrong format" in {
    val invalid1 = "4 5 6"
    intercept[ParseException] {
      InputParser.parsePoint(invalid1)
    }

    val invalid2 = "A 4"
    intercept[ParseException] {
      InputParser.parsePoint(invalid2)
    }
  }

  "should throws ParseException if the line with commands has a wrong format" in {
    val invalid1 = "LMLMLMLA"
    intercept[ParseException] {
      InputParser.parseCommands(invalid1)
    }

    val invalid2 = "LMLMLML87"
    intercept[ParseException] {
      InputParser.parseCommands(invalid2)
    }
  }

  "should throws ParseException if the line with a start state has a wrong format" in {
    val invalid1 = "5 A"
    intercept[ParseException] {
      InputParser.parseStartState(invalid1)
    }

    val invalid2 = "5 A N"
    intercept[ParseException] {
      InputParser.parseStartState(invalid2)
    }

    val invalid3 = "5 5 D"
    intercept[ParseException] {
      InputParser.parseStartState(invalid3)
    }
  }


  private implicit def string2Stream(string: String): InputStream = {
    new ByteArrayInputStream(string.getBytes("utf-8"))
  }
}
