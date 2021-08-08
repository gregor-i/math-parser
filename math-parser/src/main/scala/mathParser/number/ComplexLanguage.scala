package mathParser.number

import mathParser._

import mathParser.number.NumberSyntax.*
import mathParser.number.NumberOperator.*

object ComplexLanguage {
  def apply(): ComplexLanguage[Nothing] =
    DoubleLanguage()
      .mapScalar(Complex(_, 0.0))
      .addConstant("i", Complex.i)

  given LiteralParser[Complex] = atto.Atto.double.map(Complex(_, 0.0))

  given [V]: Evaluate[NumberOperator, Complex, V] = ComplexEvaluate[V]

  given [V]: Optimizer[NumberOperator, Complex, V] = NumberOptimizer[Complex, V]

  given [V]: Derive[NumberOperator, Complex, V] = NumberDerive[Complex, V]
}
