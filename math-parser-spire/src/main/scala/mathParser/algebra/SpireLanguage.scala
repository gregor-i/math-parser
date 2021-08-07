package mathParser
package algebra

import mathParser.number.{DoubleLanguage, NumberBinaryOperator, NumberOperator, NumberUnitaryOperator}
import mathParser.number.NumberOperator.*
import mathParser.number.NumberSyntax.*
import mathParser.AbstractSyntaxTree.*
import spire.algebra.{Field, NRoot, Trig}

import scala.util.Try
import mathParser.{AbstractSyntaxTree, Evaluate}

object SpireLanguage {
  def apply[A: Field: NRoot: Trig]: Language[NumberOperator, A, Nothing] =
    DoubleLanguage().mapScalar(Field[A].fromDouble)

  given [A : Field]: mathParser.number.Number[A] = mathParser.number.Number.contraMap(Field[A].fromDouble)

  given spireLiteralParser[A: Field]: LiteralParser[A] = s => s.toDoubleOption.map(Field[A].fromDouble)

  given [A: Field: NRoot: Trig, V]: Evaluate[NumberOperator, A, V] = SpireEvaluate()

  given [A: Field: NRoot: Trig, V]: Optimizer[NumberOperator, A, V] =
    mathParser.number.NumberOptimizer()

  given [A:Field:NRoot:Trig, V]: Derive[NumberOperator, A, V] =
    mathParser.number.NumberDerive()

}
