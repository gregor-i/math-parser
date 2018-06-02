package mathParser.boolean

import mathParser.slices.{BinaryOperator, Constant, LanguageOperators, UnitaryOperator}

trait BooleanOperators extends LanguageOperators{
  type S = Boolean
  type Constant = BooleanConstant
  type UnitaryOperator = BooleanUnitaryOperator
  type BinaryOperator = BooleanBinaryOperator

  def unitaryOperators: Seq[UnitaryOperator] = Seq(Not)
  def binaryOperators: Seq[BinaryOperator] = Seq.empty
  def binaryInfixOperators: Seq[BinaryOperator] = Seq(And, Or, Equals, Unequals)
  def constants(): Seq[Constant] = Seq(`true`, `false`)
}


sealed abstract class BooleanUnitaryOperator(val symbol:Symbol, val apply:(Boolean => Boolean)) extends UnitaryOperator[Boolean]
case object Not extends BooleanUnitaryOperator('!, !_)

sealed abstract class BooleanBinaryOperator(val symbol:Symbol, val apply:((Boolean, Boolean)=> Boolean)) extends BinaryOperator[Boolean]
case object And extends BooleanBinaryOperator('&, _ && _)
case object Or extends BooleanBinaryOperator('|, _ || _)
case object Equals extends BooleanBinaryOperator('=, _ == _)
case object Unequals extends BooleanBinaryOperator('!=, _ != _)

abstract class BooleanConstant(val symbol:Symbol, val value: Boolean) extends Constant[Boolean]
case object `true` extends BooleanConstant('true, true)
case object `false` extends BooleanConstant('false, false)
