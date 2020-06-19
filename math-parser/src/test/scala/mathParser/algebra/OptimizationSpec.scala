package mathParser.algebra

import mathParser.Implicits._
import mathParser.{Language, LiteralParser, MathParser, Optimizer}
import org.scalatest.funsuite.AnyFunSuite

class OptimizationSpec extends AnyFunSuite{

  case object X

  testTemplate(MathParser.complexLanguage, "complex language")

  def testTemplate[UO, BO, S](_lang: Language[UO, BO, S, Nothing], langName: String)
                             (implicit litParser: LiteralParser[S], optimizier: Optimizer[UO, BO, S, X.type])= {
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
        assert(lang.optimize(left.get) === right.get)
      }

  }
}
