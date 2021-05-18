package mathParser.complex

import mathParser.{Derive, Evaluate, LiteralParser, Optimizer}

object ComplexImplicits {
  given LiteralParser[Complex] = ComplexLanguage.complexLiteralParser

  given [V]: Evaluate[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexEvaluate

  given [V]: Optimizer[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexOptimizer

  given [V]: Derive[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexDerive
}
