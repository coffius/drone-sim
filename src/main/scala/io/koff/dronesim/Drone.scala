package io.koff.dronesim

/**
  * Represents a drone which can fly around the platform
  */
class Drone(private var currPos: Point, private var currDir: Direction) {
  /**
    * Returns the current position of the drone
    */
  def pos: Point = currPos

  /**
    * Returns the current direction of the drone
    */
  def direction: Direction = currDir
}
