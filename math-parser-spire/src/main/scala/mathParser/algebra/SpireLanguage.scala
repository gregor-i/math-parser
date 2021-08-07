package mathParser
package algebra

import mathParser.number.{NumberBinaryOperator, NumberUnitaryOperator}
import mathParser.number.NumberOperator
import mathParser.number.NumberOperator.*
import mathParser.number.NumberSyntax.*
import mathParser.AbstractSyntaxTree.*
import spire.algebra.{Field, NRoot, Trig}

import scala.util.Try
import mathParser.{AbstractSyntaxTree, Evaluate}

object SpireLanguage {
  def apply[A: Field: NRoot: Trig]: Language[NumberOperator, A, Nothing] =
    Language.emptyLanguage
      .withConstants[A](List("e" -> Trig[A].e, "pi" -> Trig[A].pi))
      .withBinaryOperators[NumberBinaryOperator](prefix = List.empty, infix = List(Plus, Minus, Times, Divided, Power).map(op => (op.name, op)))
      .withUnitaryOperators(List(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log).map(op => (op.name, op)))

  given[A] (using field: Field[A]): mathParser.number.Number[A] = mathParser.number.Number.contraMap(field.fromDouble)

  given spireLiteralParser[A: Field]: LiteralParser[A] = s => s.toDoubleOption.map(Field[A].fromDouble)

  given [A: Field: NRoot: Trig, V]: Evaluate[NumberOperator, A, V] = SpireEvaluate()

  given [A: Field: NRoot: Trig, V]: Optimizer[NumberOperator, A, V] =
    mathParser.number.NumberOptimizer()

  given [A:Field:NRoot:Trig, V]: Derive[NumberOperator, A, V] =
    mathParser.number.NumberDerive()

}
