package mathParser.algebra

import mathParser.SpireImplicits._
import mathParser.SpireLanguages
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import spire.math.Complex

class ParseComplexSpec extends AnyFunSuite with Matchers {
  val lang = SpireLanguages.complexLanguage

  import lang.{constantNode, optimize, parse}

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
