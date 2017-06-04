package scoder

object DecodeOk {

  def apply[T](t: T): DecodeResult[Nothing, T] =
    DecodeResult(Right(t))

  def unapply[E, T](result: DecodeResult[E, T]): Option[T] = result match {
    case DecodeResult(Right(t)) => Some(t)
    case _ => None
  }
}
