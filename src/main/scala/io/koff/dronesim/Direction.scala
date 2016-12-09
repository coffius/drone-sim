package io.koff.dronesim

/**
  * Direction
  */
sealed abstract class Direction(val inputName: Char)

object Direction {
  case object North extends Direction('N')
  case object East  extends Direction('E')
  case object South extends Direction('S')
  case object West  extends Direction('W')
}
