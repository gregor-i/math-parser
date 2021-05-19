package mathParser.number

import mathParser._

import NumberBinaryOperator.*
import NumberUnitaryOperator.*
import mathParser.number.NumberSyntax.*

object ComplexLanguage {
  def apply(): ComplexLanguage[Nothing] =
    Language.emptyLanguage
      .withConstants[Complex](List("e" -> Complex.e, "pi" -> Complex.pi, "i" -> Complex.i))
      .withBinaryOperators[NumberBinaryOperator](prefix = List.empty, infix = List(Plus, Minus, Times, Divided, Power).map(op => (op.name, op)))
      .withUnitaryOperators(List(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log).map(op => (op.name, op)))

  given LiteralParser[Complex] = _.toDoubleOption.map(Complex(_, 0.0))

  given [V]: Evaluate[NumberUnitaryOperator, NumberBinaryOperator, Complex, V] = ComplexEvaluate[V]

  given [V]: Optimizer[NumberUnitaryOperator, NumberBinaryOperator, Complex, V] = NumberOptimizer[Complex, V]

  given [V]: Derive[NumberUnitaryOperator, NumberBinaryOperator, Complex, V] = NumberDerive[Complex, V]
}
