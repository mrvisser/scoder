package scoder

case class DecodeResult[+E, +T](toEither: Either[E, T]) {

  def map[B](f: T => B): DecodeResult[E, B] = DecodeResult(toEither.right.map(f))

  def flatMap[E0 >: E, B](f: T => DecodeResult[E0, B]): DecodeResult[E0, B] =
    DecodeResult(toEither.right.flatMap(f(_).toEither))

  def recoverWith[E0 >: E, T0 >: T](f: PartialFunction[E, DecodeResult[E0, T0]]): DecodeResult[E0, T0] =
    toEither match {
      case Left(e) if f.isDefinedAt(e) => f(e)
      case _ => this
    }

  def get: T = toEither.right.get
  def getOrElse[T0 >: T](t: => T0): T0 = toEither.right.getOrElse(t)

  def error: E = toEither.left.get
  def errorOrElse[E0 >: E](e: => E0): E0 = toEither.left.getOrElse(e)

  def isOk: Boolean = toEither.isRight
  def isFail: Boolean = toEither.isLeft
}

object DecodeResult {
  def fail[E, T](e: E): DecodeResult[E, T] = DecodeResult(Left(e))
  def ok[E, T](t: T): DecodeResult[E, T] = DecodeResult(Right(t))
}