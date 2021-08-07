package mathParser

import mathParser.boolean.{BooleanLanguage}
import mathParser.number.{Complex, ComplexLanguage, DoubleLanguage}
import mathParser.number.NumberOperator
import mathParser.boolean.BooleanOperator

object BuildIn {
  val booleanLanguage: Language[BooleanOperator, Boolean, Nothing] =
    BooleanLanguage()

  val doubleLanguage: Language[NumberOperator, Double, Nothing] =
    DoubleLanguage()

  val complexLanguage: Language[NumberOperator, Complex, Nothing] =
    ComplexLanguage()
}
