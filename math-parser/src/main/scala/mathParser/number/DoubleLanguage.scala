package mathParser.number

import mathParser._
import mathParser.number.NumberSyntax

object DoubleLanguage {
  def apply(): Language[NumberUnitaryOperator, NumberBinaryOperator, Double, Nothing] =
    Language.emptyLanguage
      .withConstants[Double](List("e" -> Math.E, "pi" -> Math.PI))
      .withBinaryOperators[NumberBinaryOperator](prefix = List.empty, infix = List(Plus, Minus, Times, Divided, Power).map(op => (op.name, op)))
      .withUnitaryOperators(List(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log).map(op => (op.name, op)))

  given LiteralParser[Double] = _.toDoubleOption

  given [V]: Evaluate[NumberUnitaryOperator, NumberBinaryOperator, Double, V] = DoubleEvaluate[V]

  given [V]: Optimizer[NumberUnitaryOperator, NumberBinaryOperator, Double, V] = NumberOptimizer[Double, V]

  given [V]: Derive[NumberUnitaryOperator, NumberBinaryOperator, Double, V] = NumberDerive[Double, V]
}
