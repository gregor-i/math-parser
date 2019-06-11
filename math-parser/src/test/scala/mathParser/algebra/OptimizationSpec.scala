package mathParser.algebra

import mathParser.{SomeFunctions, TestUtils}
import org.scalacheck.Gen
import org.scalacheck.Prop._
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, Matchers}

class OptimizationSpec extends FunSuite with Matchers with Checkers with TestUtils {

  val lang = mathParser.MathParser.doubleLanguage('x)

  import lang._

  val x = Variable('x)

  test("neg(neg(x)) == x") {
    optimize(neg(neg(x))) shouldBe x
    optimize(neg(neg(neg(x)))) shouldBe neg(x)
  }

  test("x + 0 == x and 0 + x == x") {
    optimize(zero + x) shouldBe x
    optimize(x + zero) shouldBe x
  }

  test("x * 1 == x and 1 * x == x") {
    optimize(one * x) shouldBe x
    optimize(x * one) shouldBe x
  }

  test("x * 0 == 0 and 0 * x == x") {
    optimize(zero * x) shouldBe zero
    optimize(x * zero) shouldBe zero
  }

  test("log(exp(x)) == x") {
    optimize(log(exp(x))) shouldBe x
  }

  test("replace constants") {
    optimize(one + one) shouldBe two
    optimize(one + exp(zero)) shouldBe two
  }

  test("x - x = 0") {
    optimize(x - x) shouldBe zero
    optimize((x * two) - (x * two)) shouldBe zero
  }

  test("x / x = 1") {
    optimize(x / x) shouldBe one
    optimize((x + one) / (x + one)) shouldBe one
  }

  test("combined example") {
    optimize((sin(x) * zero) + x + neg(x)) shouldBe zero
  }

  test("all examples stay the same after optimization") {
    check(forAll(SomeFunctions.someFunctions) { f =>
      val parsed = parse(f).get
      val optimized = optimize(parsed)
      forAll(Gen.choose(-1000d, 1000d)) {
        x: Double =>
          evaluate(parsed) { case 'x => x } ==== evaluate(optimized) { case 'x => x }
      }
    })
  }
}
