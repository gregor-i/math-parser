package mathParser.number

import mathParser.number.{ComplexLanguage, DoubleLanguage}
import mathParser.{BuildIn, Language, LiteralParser, Optimizer}
import org.scalatest.funsuite.AnyFunSuite

trait OptimizationProp { self: AnyFunSuite =>
  private def identities = Seq(
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

  def testOptimization[S: LiteralParser](
      rawLang: Language[NumberOperator, S, Nothing]
  )(using optimizier: Optimizer[NumberOperator, S]) = {
    case object X
    val lang = rawLang.addVariable("x", X)
    for (id <- identities)
      test(s"identity ${id._1} == ${id._2}") {
        val left  = lang.parse(id._1)
        val right = lang.parse(id._2)
        if (left.isEmpty || right.isEmpty)
          cancel("parsing failed")
        assert(left.get.optimize === right.get)
      }
  }
}
