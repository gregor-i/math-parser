package mathParser.number

import mathParser.{Derive, Language, LiteralParser, Optimizer}
import org.scalatest.funsuite.AnyFunSuite

trait NumberProps extends ParseProp with DeriveProps with OptimizationProp { self: AnyFunSuite =>
  def testNumerLanguage[S](
                            lang: Language[NumberOperator, S, Nothing],
                          )(implicit litParser: LiteralParser[S],
                            derive: Derive[NumberOperator, S],
                            optimizier: Optimizer[NumberOperator, S]) = {
    testParsing(lang)
    testDerive(lang)
    testOptimization(lang)
  }
}
