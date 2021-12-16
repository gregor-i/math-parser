package mathParser.number

import mathParser.number.{ComplexLanguage, DoubleLanguage}
import mathParser.{BuildIn, Derive, Language, LiteralParser}
import org.scalactic.Tolerance.convertNumericToPlusOrMinusWrapper
import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite

trait DeriveProps { self: AnyFunSuite =>
  def testDerive[S: LiteralParser](rawLang: Language[NumberOperator, S, Nothing])(implicit derive: Derive[NumberOperator, S]) = {
    case object X
    val lang = rawLang.addVariable("x", X)
    import lang.parse

    test("simple functions") {
      assert(parse("x*x").get.derive(X) === parse("1*x+1*x").get)
      assert(parse("x + x").get.derive(X) === parse("1 + 1").get)
      assert(parse("x*x + x").get.derive(X) === parse("(1*x+1*x) + 1").get)
    }

    test("all the sample functions are derivable") {
      for (function <- SomeFunctions.someFunctions)
        parse(function).get.derive(X)
    }
  }
}
