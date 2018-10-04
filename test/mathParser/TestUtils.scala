package mathParser

import org.scalacheck.{Arbitrary, Gen}
import spire.math.Complex

trait TestUtils {
  val genComplex: Gen[Complex[Double]] = for {
    imag <- Arbitrary.arbDouble.arbitrary
    real <- Arbitrary.arbDouble.arbitrary
  } yield Complex(imag, real)

  implicit class DoubleEquality(left: Double) {
    def ====(right: Double): Boolean = (left == right) || (left != left && right != right)
  }

  implicit class ComplexDoubleEquality(left: Complex[Double]) {
    def ====(right: Complex[Double]): Boolean = (left.imag ==== right.imag) && (left.real ==== right.real)
  }
}
