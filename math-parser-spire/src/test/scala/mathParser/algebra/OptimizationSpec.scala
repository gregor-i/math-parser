package mathParser.algebra

import mathParser.Language
import mathParser.number.{NumberBinaryOperator, NumberUnitaryOperator}
import spire.implicits.given
import mathParser.algebra.SpireLanguage.given
import mathParser.{LiteralParser, SpireLanguages}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import spire.algebra.{Field, NRoot, Trig}

class OptimizationSpec extends AnyFunSuite with Matchers {

  case object X

  testLanguage(SpireLanguages.doubleLanguage, "double language")
  testLanguage(SpireLanguages.realLanguage, "real language")
  testLanguage(SpireLanguages.complexLanguage, "complex language")

  def testLanguage[A: Field: Trig: NRoot: LiteralParser](_lang: Language[NumberUnitaryOperator, NumberBinaryOperator, A, Nothing], langName: String) = {
    val lang = _lang.withVariables[X.type](List("x" -> X))

    val identities = Seq(
      "--x"                 -> "x",
      "---x"                -> "-x",
      "x + 0"               -> "x",
      "0 + x"               -> "x",
      "x * 1"               -> "x",
      "1 * x"               -> "x",
      "x * 0"               -> "0",
      "0 * x"               -> "0",
      "x ^ 1"               -> "x",
      "x ^ 0"               -> "1",
      "0 ^ x"               -> "0",
      "1 ^ x"               -> "1",
      "log(exp(x))"         -> "x",
      "1 + 1"               -> "2",
      "1 + exp(0)"          -> "2",
      "x - x"               -> "0",
      "2*x - 2*x"           -> "0",
      "x / x"               -> "1",
      "(x+1) / (x+1)"       -> "1",
      "sin(x) * 0 + x + -x" -> "0"
    )

    for (id <- identities)
      test(s"$langName: ${id._1} == ${id._2}") {
        val left  = lang.parse(id._1)
        val right = lang.parse(id._2)
        if (left.isEmpty || right.isEmpty)
          cancel("parsing failed")
        lang.optimize(left.get) shouldBe right.get
      }

  }
}
