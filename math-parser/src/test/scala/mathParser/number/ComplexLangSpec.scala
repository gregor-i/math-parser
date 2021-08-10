package mathParser.number

import mathParser.number.ComplexLanguage.given
import mathParser.BuildIn
import mathParser.number.Complex
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import mathParser.number.NumberSyntax.constant

class ComplexLangSpec extends AnyFunSuite with NumberProps {
  val lang = BuildIn.complexLanguage
  testNumerLanguage(lang)

  test("parse complex literals") {
    import lang.parse
    assert(parse("i").map(_.optimize) === Some(Complex(0, 1).constant[Nothing]))
    assert(parse("5*i").map(_.optimize) === Some(Complex(0, 5).constant[Nothing]))
    assert(parse("i + i").map(_.optimize) === Some(Complex(0, 2).constant[Nothing]))
    assert(parse("i*i").map(_.optimize) === Some(Complex(-1, 0).constant[Nothing]))
    assert(parse("-i").map(_.optimize) === Some(Complex(0, -1).constant[Nothing]))
    assert(parse("1+i").map(_.optimize) === Some(Complex(1, 1).constant[Nothing]))
    assert(parse("1-i").map(_.optimize) === Some(Complex(1, -1).constant[Nothing]))
  }
}
