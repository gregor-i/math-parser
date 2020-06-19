package mathParser.complex

import mathParser.{Derive, Evaluate, LiteralParser, Optimizer}


trait ComplexImplicits {
  implicit final def spireLiteralParser: LiteralParser[Complex] =
    ComplexLanguage.complexLiteralParser

  implicit final def spireEvaluate[V]: Evaluate[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexEvaluate

  implicit final def spireOptimizer[V]: Optimizer[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexOptimizer

  implicit final def spireDerive[V]: Derive[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    ComplexLanguage.complexDerive
}
