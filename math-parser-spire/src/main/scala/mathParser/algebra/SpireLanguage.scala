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

  given [A: Field]: mathParser.number.Number[A] = mathParser.number.Number.contraMap(Field[A].fromDouble)

  given spireLiteralParser[A: Field]: LiteralParser[A] =
    mathParser.number.DoubleLanguage.given_LiteralParser_Double.map(Field[A].fromDouble)

  given [A: Field: NRoot: Trig]: Evaluate[NumberOperator, A] = SpireEvaluate()

  given [A: Field: NRoot: Trig]: Optimizer[NumberOperator, A] =
    mathParser.number.NumberOptimizer()

  given [A: Field: NRoot: Trig]: Derive[NumberOperator, A] =
    mathParser.number.NumberDerive()
}
