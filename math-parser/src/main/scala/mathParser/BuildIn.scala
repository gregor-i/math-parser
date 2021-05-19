package mathParser

import mathParser.boolean.{BooleanBinaryOperator, BooleanLanguage, BooleanUnitaryOperator}
import mathParser.number.{Complex, ComplexLanguage, DoubleLanguage, NumberBinaryOperator, NumberUnitaryOperator}

object BuildIn {
  val booleanLanguage: Language[BooleanUnitaryOperator, BooleanBinaryOperator, Boolean, Nothing] =
    BooleanLanguage()

  val doubleLanguage: Language[NumberUnitaryOperator, NumberBinaryOperator, Double, Nothing] =
    DoubleLanguage()

  val complexLanguage: Language[NumberUnitaryOperator, NumberBinaryOperator, Complex, Nothing] =
    ComplexLanguage()
}
