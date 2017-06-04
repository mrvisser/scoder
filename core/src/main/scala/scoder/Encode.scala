package scoder

trait Encode[+F, -T] {
  def apply(to: T): F = encode(to)
  def encode(to: T): F
}
object Encode {

  def apply[F, T](f: T => F): Encode[F, T] = new Encode[F, T] {
    override def encode(to: T): F = f(to)
  }

}