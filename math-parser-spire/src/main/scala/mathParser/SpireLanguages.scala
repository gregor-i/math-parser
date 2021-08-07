package mathParser

import mathParser.algebra.SpireLanguage
import mathParser.boolean.{BooleanLanguage}
import mathParser.number.{NumberOperator}
import spire.implicits.*
import spire.math.{Complex, Real}
import spire.algebra.Field

object SpireLanguages {
  val doubleLanguage: Language[NumberOperator, Double, Nothing] =
    SpireLanguage[Double]

  val complexLanguage: Language[NumberOperator, Complex[Double], Nothing] =
    SpireLanguage[Complex[Double]]
      .addConstant("i", Complex.i[Double])

  val realLanguage: Language[NumberOperator, Real, Nothing] =
    SpireLanguage[Real]
}
