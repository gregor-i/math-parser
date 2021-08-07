package mathParser.boolean

import mathParser.{BinaryOperator, Evaluate, Language, UnitaryOperator}
import mathParser.boolean.BooleanOperator.*

object BooleanLanguage {
  def apply(): Language[BooleanOperator, Boolean, Nothing] =
    Language[BooleanOperator, Boolean, Nothing](
      unitaryOperators = List(Not).map(op => (op.name, op)),
      binaryPrefixOperators = List.empty,
      binaryInfixOperators = List(And, Or, Equals, Unequals).map(op => (op.name, op)),
      constants = List("true" -> true, "false" -> false),
      variables = List.empty
    )

  given [V]: Evaluate[BooleanOperator, Boolean, V] =
    new Evaluate[BooleanOperator, Boolean, V] {
      override def executeUnitary(uo: BooleanOperator & UnitaryOperator, s: Boolean): Boolean = uo match {
        case Not => !s
      }
      override def executeBinaryOperator(bo: BooleanOperator & BinaryOperator, left: Boolean, right: Boolean): Boolean = bo match {
        case And => left && right
        case Or => left || right
        case Equals => left == right
        case Unequals =>  left != right
      }
    }
}
