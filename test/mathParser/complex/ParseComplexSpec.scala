package mathParser.complex

import mathParser.MathParser
import org.scalatest.{FunSuite, Matchers}
import spire.math.Complex

class ParseComplexSpec extends FunSuite with Matchers {
  val lang = MathParser.complexLanguage('x)

  import lang._

  test("parse complex literals") {
    optimize(parse("i").get) shouldBe ConstantNode(Complex(0, 1))
    optimize(parse("5*i").get) shouldBe ConstantNode(Complex(0, 5))
    optimize(parse("i + i").get) shouldBe ConstantNode(Complex(0, 2))
    optimize(parse("i*i").get) shouldBe ConstantNode(Complex(-1, 0))
    optimize(parse("-i").get) shouldBe ConstantNode(Complex(0, -1))
    optimize(parse("1+i").get) shouldBe ConstantNode(Complex(1, 1))
    optimize(parse("1-i").get) shouldBe ConstantNode(Complex(1, -1))
  }
}
