package mathParser

import mathParser.boolean.{BooleanBinaryOperator, BooleanLanguage, BooleanUnitaryOperator}
import mathParser.complex.{Complex, ComplexBinaryOperator, ComplexLanguage, ComplexUnitaryOperator}

object MathParser {
  val booleanLanguage: Language[BooleanUnitaryOperator, BooleanBinaryOperator, Boolean, Nothing] =
    BooleanLanguage()

  val complexLanguage: Language[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, Nothing] =
    ComplexLanguage()
}
