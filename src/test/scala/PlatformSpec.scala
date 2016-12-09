import io.koff.dronesim.Direction._
import io.koff.dronesim.DroneCommand._
import io.koff.dronesim.Platform.{InvalidInitPosition, OutOfBoundaries}
import io.koff.dronesim.{Platform, Point}
import org.scalatest._

/**
  * Test for the Platform class
  */
class PlatformSpec extends FlatSpec with Matchers {
  it should "work with the empty list of commands" in {
    val platform = new Platform(Point(5, 5))
    val result = platform.simulateDrone(startPos = Point(1, 1), startDir = North, Seq.empty)

    result shouldBe a[Right[_, _]]
    val drone = result.right.get
    drone.pos shouldBe Point(1, 1)
    drone.direction shouldBe North
  }

  it should "simulate drone movement correctly. Example #1" in {
    val platform = new Platform(Point(5, 5))
    val result = platform.simulateDrone(
      Point(1, 2), North, Seq(TurnLeft, Move, TurnLeft, Move, TurnLeft, Move, TurnLeft, Move, Move)
    )

    result shouldBe a[Right[_, _]]
    val drone = result.right.get
    drone.pos shouldBe Point(1, 3)
    drone.direction shouldBe North
  }

  it should "simulate drone movement correctly. Example #2" in {
    val platform = new Platform(Point(5, 5))
    val result = platform.simulateDrone(
      Point(3, 3), East, Seq(Move, Move, TurnRight, Move, Move, TurnRight, Move, TurnRight, TurnRight, Move)
    )

    result shouldBe a[Right[_, _]]
    val drone = result.right.get
    drone.pos shouldBe Point(5, 1)
    drone.direction shouldBe East
  }

  it should "return InvalidInitPosition(...) if a drone has a wrong start position" in {
    val platform = new Platform(Point(5, 5))
    val result = platform.simulateDrone(Point(-3, -3), North, Seq.empty)
    result shouldBe Left(InvalidInitPosition(Point(-3, -3)))
  }

  it should "return OutOfBoundaries(...) if a drone leaves the platform" in {
    val platform = new Platform(Point(5, 5))
    val result = platform.simulateDrone(Point(0, 0), North, Seq(TurnRight, Move, Move, Move, Move, Move, Move))
    result shouldBe Left(OutOfBoundaries(Point(6, 0), East))
  }
}
