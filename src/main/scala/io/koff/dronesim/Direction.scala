package io.koff.dronesim

/**
  * Direction
  */
sealed abstract class Direction(val inputName: Char,
                                val moveVec: Point) {
  def left: Direction
  def right: Direction
}

object Direction {
  case object North extends Direction('N', Point( 0,  1)) {
    override def left: Direction = West
    override def right: Direction = East
  }

  case object East  extends Direction('E', Point( 1,  0)) {
    override def left: Direction = North
    override def right: Direction = South
  }

  case object South extends Direction('S', Point( 0, -1)) {
    override def left: Direction = East
    override def right: Direction = West
  }

  case object West  extends Direction('W',  Point(-1,  0)) {
    override def left: Direction = South
    override def right: Direction = North
  }

}
