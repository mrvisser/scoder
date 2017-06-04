package scoder

import scala.util.Try

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

class DecodeResultSpec extends Properties("DecodeResult") with Generators {

  property("toEither") = forAll { (either: Either[String, Int]) =>
    DecodeResult(either).toEither == either
  }

  property("map") = forAll { (result: DecodeResult[String, Int]) =>
    result.map(_.toString).toEither == result.toEither.right.map(_.toString)
  }

  property("map") = forAll { (left: Left[String, Int]) =>
    Try(DecodeResult(left).map(_ => throw new Exception("oops"))).isSuccess
  }

  property("flatMap") = forAll { (result: DecodeResult[String, Int]) =>
    result.flatMap(_ => result) == result
  }

  property("flatMap") = forAll { (either: Either[String, Int]) =>
    DecodeResult(either).flatMap(_ => DecodeResult(Left("oops"))).isFail
  }

  property("recoverWith") = forAll { (result: DecodeResult[String, Int], recovered: DecodeResult[String, Int]) =>
    if (result.isOk)
      result.recoverWith { case _ if true => recovered } == result
    else
      result.recoverWith { case _ if true => recovered } == recovered
  }

  property("recoverWith") = forAll { (result: DecodeResult[String, Int], recovered: DecodeResult[String, Int]) =>
    result.recoverWith { case a: String if false => recovered } == result
  }

}
