package mathParser.algebra

import mathParser.number.ComplexLanguage.given
import mathParser.BuildIn
import mathParser.number.Complex
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ParseComplexSpec extends AnyFunSuite with Matchers {
  val lang = BuildIn.complexLanguage

  import lang.{constantNode, optimize, parse}

  test("parse complex literals") {
    assert(parse("i").map(optimize) === Some(constantNode(Complex(0, 1))))
    assert(parse("5*i").map(optimize) === Some(constantNode(Complex(0, 5))))
    assert(parse("i + i").map(optimize) === Some(constantNode(Complex(0, 2))))
    assert(parse("i*i").map(optimize) === Some(constantNode(Complex(-1, 0))))
    assert(parse("-i").map(optimize) === Some(constantNode(Complex(0, -1))))
    assert(parse("1+i").map(optimize) === Some(constantNode(Complex(1, 1))))
    assert(parse("1-i").map(optimize) === Some(constantNode(Complex(1, -1))))
  }
}
