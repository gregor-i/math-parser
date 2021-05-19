package mathParser.complex

import mathParser._

import ComplexBinaryOperator.*
import ComplexUnitaryOperator.*
import mathParser.complex.Syntax.*

object ComplexLanguage {
  def apply(): ComplexLanguage[Nothing] =
    Language.emptyLanguage
      .withConstants[Complex](List("e" -> Complex.e, "pi" -> Complex.pi, "i" -> Complex.i))
      .withBinaryOperators[ComplexBinaryOperator](prefix = List.empty, infix = List(Plus, Minus, Times, Divided, Power).map(op => (op.name, op)))
      .withUnitaryOperators(List(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log).map(op => (op.name, op)))

  given LiteralParser[Complex] = _.toDoubleOption.map(Complex(_, 0.0))

  given [V]: Evaluate[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] = ComplexEvaluate[V]

  given [V]: Optimizer[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] = ComplexOptimize[V]

  given [V]: Derive[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] = ComplexDerive[V]
}
