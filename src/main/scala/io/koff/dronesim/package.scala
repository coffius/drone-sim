package io.koff

import io.koff.dronesim.model.Drone

/**
  * Additional classes
  */
package object dronesim {
  /** Marker trait for simulation errors*/
  trait ErrorResult

  type SimResult  = Either [ErrorResult, Drone]

}
