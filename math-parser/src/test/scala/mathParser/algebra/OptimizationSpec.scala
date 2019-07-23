package mathParser.algebra

import mathParser.implicits._
import mathParser.{LiteralParser, MathParser}
import org.scalatest.{FunSuite, Matchers}
import spire.algebra.{Field, NRoot, Trig}


class OptimizationSpec extends FunSuite with Matchers {

  case object X

  testLanguage(MathParser.doubleLanguage, "double language")
  testLanguage(MathParser.realLanguage, "real language")
  testLanguage(MathParser.complexLanguage, "complex language")

  def testLanguage[A: Field : Trig : NRoot : LiteralParser](_lang: SpireLanguage[A, Nothing], langName: String) = {
    val lang = _lang.withVariables[X.type](List("x" -> X))

    val identities = Seq(
      "--x" -> "x",
      "---x" -> "-x",
      "x + 0" -> "x",
      "0 + x" -> "x",
      "x * 1" -> "x",
      "1 * x" -> "x",
      "x * 0" -> "0",
      "0 * x" -> "0",
      "x ^ 1" -> "x",
      "x ^ 0" -> "1",
      "0 ^ x" -> "0",
      "1 ^ x" -> "1",
      "log(exp(x))" -> "x",
      "1 + 1" -> "2",
      "1 + exp(0)" -> "2",
      "x - x" -> "0",
      "2*x - 2*x" -> "0",
      "x / x" -> "1",
      "(x+1) / (x+1)" -> "1",
      "sin(x) * 0 + x + -x" -> "0"
    )

    for (id <- identities)
      test(s"$langName: ${id._1} == ${id._2}") {
        val left = lang.parse(id._1)
        val right = lang.parse(id._2)
        if (left.isEmpty || right.isEmpty)
          cancel("parsing failed")
        lang.optimize(left.get) shouldBe right.get
      }

  }
}
