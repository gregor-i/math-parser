package mathParser.algebra

import org.scalatest.{FunSuite, Matchers}

class DeriveSpec extends FunSuite with Matchers {
  val lang = mathParser.MathParser.realLanguage('x)

  import lang._

  test("derive simple functions") {
    derive(parse("x*x").get)('x) shouldBe parse("1*x+1*x").get
    derive(parse("x + x").get)('x) shouldBe parse("1 + 1").get
    derive(parse("x*x + x").get)('x) shouldBe parse("(1*x+1*x) + 1").get
  }
}
