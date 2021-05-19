package mathParser

import mathParser.algebra.SpireLanguage
import mathParser.boolean.{BooleanBinaryOperator, BooleanLanguage, BooleanUnitaryOperator}
import mathParser.number.{NumberBinaryOperator, NumberUnitaryOperator}
import spire.implicits._
import spire.math.{Complex, Real}
import spire.algebra.Field

object SpireLanguages {
  val doubleLanguage: Language[NumberUnitaryOperator, NumberBinaryOperator, Double, Nothing] =
    SpireLanguage[Double]

  val complexLanguage: Language[NumberUnitaryOperator, NumberBinaryOperator, Complex[Double], Nothing] =
    SpireLanguage[Complex[Double]]
      .addConstant("i", Complex.i[Double])

  val realLanguage: Language[NumberUnitaryOperator, NumberBinaryOperator, Real, Nothing] =
    SpireLanguage[Real]
}
