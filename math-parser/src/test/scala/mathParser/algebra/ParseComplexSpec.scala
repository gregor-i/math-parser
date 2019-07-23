package mathParser.algebra

import mathParser.MathParser
import org.scalatest.{FunSuite, Matchers}
import spire.math.Complex
import mathParser.implicits._

class ParseComplexSpec extends FunSuite with Matchers {
  val lang = MathParser.complexLanguage

  import lang.{optimize, parse, constantNode}

  test("parse complex literals") {
    parse("i").map(optimize) shouldBe Some(constantNode(Complex(0, 1)))
    parse("5*i").map(optimize) shouldBe Some(constantNode(Complex(0, 5)))
    parse("i + i").map(optimize) shouldBe Some(constantNode(Complex(0, 2)))
    parse("i*i").map(optimize) shouldBe Some(constantNode(Complex(-1, 0)))
    parse("-i").map(optimize) shouldBe Some(constantNode(Complex(0, -1)))
    parse("1+i").map(optimize) shouldBe Some(constantNode(Complex(1, 1)))
    parse("1-i").map(optimize) shouldBe Some(constantNode(Complex(1, -1)))
  }
}
