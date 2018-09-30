package mathParser.double

import mathParser.{SomeFunctions, TestUtils}
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.scalacheck.Prop._
import org.scalacheck.{Gen, Shrink}

class DoubleOptimizationSpec extends Specification with ScalaCheck with TestUtils{

  implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny


  val lang = mathParser.MathParser.doubleLanguage('x)

  import lang._

  val x = Variable('x)

  "double optimization should apply the optimization rules" >> {
    "neg(neg(x)) == x" >> {
      optimize(neg(neg(x))) === x
      optimize(neg(neg(neg(x)))) === neg(x)
    }

    "x + 0 == x and 0 + x == x" >> {
      optimize(zero + x) === x
      optimize(x + zero) === x
    }

    "x * 1 == x and 1 * x == x" >> {
      optimize(one * x) === x
      optimize(x * one) === x
    }

    "x * 0 == 0 and 0 * x == x" >> {
      optimize(zero * x) === zero
      optimize(x * zero) === zero
    }

    "log(exp(x)) == x" >> {
      optimize(log(exp(x))) === x
    }

    "replace constants" >> {
      optimize(one + one) === ConstantNode(2d)
      optimize(one + exp(zero)) === ConstantNode(2d)
    }

    "combined example" >> {
      optimize((one * zero) + x) === x
    }

    "all examples stay the same after optimization" >> {
      forAll(SomeFunctions.someFunctions){ f =>
        val parsed = parse(f).get
        val optimized = optimize(parsed)
        forAll(Gen.choose(-1000d, 1000d)) {
          x: Double =>
            evaluate(parsed){case 'x => x } ==== evaluate(optimized){ case 'x => x }
        }
      }
    }
  }

}
