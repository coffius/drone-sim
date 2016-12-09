package io.koff

/**
  * Additional classes
  */
package object dronesim {
  /** Marker trait for simulation errors*/
  trait ErrorResult

  type Success[T] = Right [ErrorResult, T]
  type Failure    = Left  [ErrorResult, Nothing]
  type SimResult = Either [ErrorResult, Drone]

  object Success {
    def apply[S](s: S): Success[S] = new Success(s)
    def unapply[S](success: Success[S]): Option[S] = Some(success.b)
  }

  object Failure {
    def apply(f: ErrorResult): Failure = new Failure(f)
    def unapply(failure: Failure): Option[ErrorResult] = Some(failure.a)
  }
}
