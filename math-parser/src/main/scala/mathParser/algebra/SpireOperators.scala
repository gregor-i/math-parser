package mathParser.algebra

import mathParser.slices.LanguageOperators

trait SpireOperators[A] extends LanguageOperators {
  _: SpireAlgebra[A] =>

  type S = A
  type Constant = SpireConstant
  type UnitaryOperator = SpireUnitaryOperator
  type BinaryOperator = SpireBinaryOperator

  sealed abstract class SpireUnitaryOperator(val symbol:Symbol, val apply:(S => S)) extends mathParser.slices.UnitaryOperator[S]
  case object Neg extends SpireUnitaryOperator('-, field.negate)
  case object Sin extends SpireUnitaryOperator('sin, trig.sin)
  case object Cos extends SpireUnitaryOperator('cos, trig.cos)
  case object Tan extends SpireUnitaryOperator('tan, trig.tan)
  case object Asin extends SpireUnitaryOperator('asin, trig.asin)
  case object Acos extends SpireUnitaryOperator('acos, trig.acos)
  case object Atan extends SpireUnitaryOperator('atan, trig.atan)
  case object Sinh extends SpireUnitaryOperator('sinh, trig.sinh)
  case object Cosh extends SpireUnitaryOperator('cosh, trig.cosh)
  case object Tanh extends SpireUnitaryOperator('tanh, trig.tanh)
  case object Exp extends SpireUnitaryOperator('exp, trig.exp)
  case object Log extends SpireUnitaryOperator('log, trig.log)

  sealed abstract class SpireBinaryOperator(val symbol:Symbol, val apply:((S, S) => S)) extends mathParser.slices.BinaryOperator[S]
  case object Plus extends SpireBinaryOperator('+, field.plus)
  case object Minus extends SpireBinaryOperator('-, field.minus)
  case object Times extends SpireBinaryOperator('*, field.times)
  case object Divided extends SpireBinaryOperator('/, field.div)
  case object Power extends SpireBinaryOperator('^, nRoot.fpow)

  abstract class SpireConstant(val symbol:Symbol, val value: S) extends mathParser.slices.Constant[S]
  case object e extends SpireConstant('e, trig.e)
  case object pi extends SpireConstant('pi, trig.pi)

  def unitaryOperators: Seq[UnitaryOperator] = Seq(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log)
  def binaryOperators: Seq[BinaryOperator] = Seq.empty
  def binaryInfixOperators: Seq[BinaryOperator] = Seq(Plus, Minus, Times, Divided, Power)
  def constants(): Seq[Constant] = Seq(e, pi)
}
