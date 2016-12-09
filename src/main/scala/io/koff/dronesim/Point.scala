package io.koff.dronesim

/**
  * 2D coordinate
  */
case class Point(x: Int, y: Int)

object PointUtils {
  implicit class Tuple2Point(tuple: (Int, Int)) {
    def p: Point = Point.tupled(tuple)
  }
}