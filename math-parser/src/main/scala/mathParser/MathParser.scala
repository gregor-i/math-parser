package mathParser

import mathParser.boolean.{BooleanBinaryOperator, BooleanLanguage, BooleanUnitaryOperator}

object MathParser {
  val booleanLanguage: Language[BooleanUnitaryOperator, BooleanBinaryOperator, Boolean, Nothing] =
    BooleanLanguage()
}
