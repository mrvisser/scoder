package scoder

object DecodeFail {

  def apply[E](e: E): DecodeResult[E, Nothing] =
    DecodeResult(Left(e))

  def unapply[E, T](result: DecodeResult[E, T]): Option[E] = result match {
    case DecodeResult(Left(e)) => Some(e)
    case _ => None
  }
}
