package scoder

trait Decode[-F, +E, +T] {
  def apply(from: F): DecodeResult[E, T] = decode(from)
  def decode(from: F): DecodeResult[E, T]
}

object Decode {
  def apply[F, E, T](f: F => DecodeResult[E, T]): Decode[F, E, T] = new Decode[F, E, T] {
    override def decode(from: F): DecodeResult[E, T] = f(from)
  }
}