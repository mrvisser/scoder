package scoder

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary

trait Generators {
  implicit def genLeft[A: Arbitrary, B]: Arbitrary[Left[A, B]] = Arbitrary(arbitrary[A].map(Left.apply[A, B]))
  implicit def genRight[A, B: Arbitrary]: Arbitrary[Right[A, B]] = Arbitrary(arbitrary[B].map(Right.apply[A, B]))

  implicit def genDecodeResult[E: Arbitrary, T: Arbitrary]: Arbitrary[DecodeResult[E, T]] =
    Arbitrary(arbitrary[Either[E, T]].map(DecodeResult.apply))
}
