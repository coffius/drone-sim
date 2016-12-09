package io.koff.dronesim

/**
  * 2D coordinate
  */
case class Point(x: Int, y: Int) {
  def +(vector: Point): Point = {
    copy(x + vector.x, y + vector.y)
  }
}

object PointUtils {
  implicit class Tuple2Point(tuple: (Int, Int)) {
    def p: Point = Point.tupled(tuple)
  }
}