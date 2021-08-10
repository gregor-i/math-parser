package mathParser.number

import mathParser._

import mathParser.number.NumberSyntax.*
import mathParser.number.NumberOperator.*

object ComplexLanguage {
  def apply(): ComplexLanguage[Nothing] =
    DoubleLanguage()
      .mapScalar(Complex(_, 0.0))
      .addConstant("i", Complex.i)

  given LiteralParser[Complex] = _.toDoubleOption.map(Complex(_, 0.0))

  given Evaluate[NumberOperator, Complex] = ComplexEvaluate()

  given Optimizer[NumberOperator, Complex] = NumberOptimizer[Complex]

  given Derive[NumberOperator, Complex] = NumberDerive[Complex]
}
