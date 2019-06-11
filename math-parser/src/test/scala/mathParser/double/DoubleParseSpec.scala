package mathParser
package double

import org.scalatest.{FunSuite, Matchers}

class DoubleParseSpec extends FunSuite with Matchers {
  val lang = MathParser.complexLanguage('x, 'h)

  val x = lang.Variable('x)
  val h = lang.Variable('h)

  import lang._

  test("parsing constants, literals and variables") {
    parse("e") shouldBe Some(ConstantNode(Math.E))
    parse("pi") shouldBe Some(ConstantNode(Math.PI))
    parse("x") shouldBe Some(x)
    parse("undeclared") shouldBe None
    parse("2.5") shouldBe Some(ConstantNode("2.5".toDouble))
    parse("2..1") shouldBe None
  }

  test("parsing parenthesis") {
    parse("(x)") shouldBe Some(x)
    parse("((x))") shouldBe Some(x)
  }

  test("parsing binary infix operation") {
    parse("2+3") shouldBe Some(ConstantNode(2) + ConstantNode(3))
    parse("2*3") shouldBe Some(ConstantNode(2) * ConstantNode(3))
    parse("2^3") shouldBe Some(ConstantNode(2) ^ ConstantNode(3))
  }

  test("parsing binary infix operators and following the operator priority") {
    parse("1+2*3") shouldBe Some(ConstantNode(1) + (ConstantNode(2) * ConstantNode(3)))
    parse("1*2+3") shouldBe Some((ConstantNode(1) * ConstantNode(2)) + ConstantNode(3))
  }

  test("parsing unitary prefix operators") {
    parse("sin(x)") shouldBe Some(sin(x))
    parse("sin()") shouldBe None
    parse("sin(x") shouldBe None
    parse("sin(x)+x") shouldBe Some(sin(x) + x)
    parse("sin(x)+") shouldBe None
    parse("sin (x)") shouldBe Some(sin(x))
    parse("sin (x)+x") shouldBe Some(sin(x) + x)
  }

  test("parse prefix nagative operator") {
    parse("-x") shouldBe Some(neg(x))
    parse("sinx") shouldBe None
    parse("sinh") shouldBe None
    parse("sinh(x)") shouldBe Some(sinh(x))
    parse("sinh x") shouldBe Some(sinh(x))
    parse("sinh x + 2") shouldBe Some(sinh(x) + ConstantNode(2))
  }

  test("parse complex situations correctly") {
    parse("(x-1)^4*x^4") shouldBe parse("((x-1)^4) * (x^4)")
    parse("x^-x") shouldBe parse("x^(-x)")
    parse("sin(x)*sin(5*x)") shouldBe parse("(sin(x))*(sin(5*x))")
  }
}
