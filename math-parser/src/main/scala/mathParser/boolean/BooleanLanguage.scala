package mathParser.boolean

import mathParser.{Evaluate, Language}

import BooleanBinaryOperator.*
import BooleanUnitaryOperator.*

object BooleanLanguage {
  def apply(): Language[BooleanUnitaryOperator, BooleanBinaryOperator, Boolean, Nothing] =
    Language[BooleanUnitaryOperator, BooleanBinaryOperator, Boolean, Nothing](
      unitaryOperators = List(Not).map(op => (op.name, op)),
      binaryPrefixOperators = List.empty,
      binaryInfixOperators = List(And, Or, Equals, Unequals).map(op => (op.name, op)),
      constants = List("true" -> true, "false" -> false),
      variables = List.empty
    )

  given [V]: Evaluate[BooleanUnitaryOperator, BooleanBinaryOperator, Boolean, V] =
    new Evaluate[BooleanUnitaryOperator, BooleanBinaryOperator, Boolean, V] {
      override def executeUnitary(uo: BooleanUnitaryOperator, s: Boolean): Boolean = uo.apply(s)
      override def executeBinaryOperator(bo: BooleanBinaryOperator, left: Boolean, right: Boolean): Boolean = bo.apply(left, right)
    }
}
