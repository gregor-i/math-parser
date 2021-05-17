package mathParser.complex

import mathParser.{Derive, Evaluate, LiteralParser, Optimizer}

trait ComplexImplicits {
  implicit final def complexLiteralParser: LiteralParser[Complex] =
    ComplexLanguage.complexLiteralParser

  implicit final def complexEvaluate[V]: Evaluate[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexEvaluate

  implicit final def complexOptimizer[V]: Optimizer[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexOptimizer

  implicit final def complexDerive[V]: Derive[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexDerive
}
