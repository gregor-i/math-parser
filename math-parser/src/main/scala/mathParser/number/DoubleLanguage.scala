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

  given LiteralParser[Double] = _.toDoubleOption

  given Evaluate[NumberOperator, Double] = DoubleEvaluate()

  given Optimizer[NumberOperator, Double] = NumberOptimizer[Double]

  given Derive[NumberOperator, Double] = NumberDerive[Double]
}
