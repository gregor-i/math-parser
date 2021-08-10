package mathParser.boolean

import mathParser.{BinaryOperator, Evaluate, Language, UnitaryOperator}
import mathParser.boolean.BooleanOperator.*

object BooleanLanguage {
  def apply(): Language[BooleanOperator, Boolean, Nothing] =
    Language.emptyLanguage
      .addUnitaryOperator(Not.name, Not)
      .addBinaryInfixOperator(And.name, And)
      .addBinaryInfixOperator(Or.name, Or)
      .addBinaryInfixOperator(Equals.name, Equals)
      .addBinaryInfixOperator(Unequals.name, Unequals)
      .addConstant("true", true)
      .addConstant("false", false)

  given Evaluate[BooleanOperator, Boolean] =
    new Evaluate[BooleanOperator, Boolean] {
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
