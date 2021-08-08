package mathParser.number

import mathParser._
import mathParser.number.NumberSyntax
import mathParser.number.NumberOperator.*

object DoubleLanguage {
  def apply(): Language[NumberOperator, Double, Nothing] =
    Language.emptyLanguage
      .withConstants[Double](List("e" -> Math.E, "pi" -> Math.PI))
      .withBinaryOperators[NumberBinaryOperator](prefix = List.empty, infix = List(Plus, Minus, Times, Divided, Power).map(op => (op.name, op)))
      .withUnitaryOperators(List(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log).map(op => (op.name, op)))

  given LiteralParser[Double] = atto.Atto.double

  given [V]: Evaluate[NumberOperator, Double, V] = DoubleEvaluate[V]

  given [V]: Optimizer[NumberOperator, Double, V] = NumberOptimizer[Double, V]

  given [V]: Derive[NumberOperator, Double, V] = NumberDerive[Double, V]
}
