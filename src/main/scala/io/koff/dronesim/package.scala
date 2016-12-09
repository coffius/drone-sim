package io.koff

/**
  * Additional classes
  */
package object dronesim {
  /** Marker trait for simulation errors*/
  trait ErrorResult

  type SimResult  = Either [ErrorResult, Drone]

}
