package mathParser.double

import mathParser.slices.{BinaryOperator, Constant, LanguageOperators, UnitaryOperator}

trait DoubleOperators extends LanguageOperators {
  type S = Double
  type Constant = DoubleConstant
  type UnitaryOperator = DoubleUnitaryOperator
  type BinaryOperator = DoubleBinaryOperator

  def unitaryOperators: Seq[UnitaryOperator] = Seq(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log)
  def binaryOperators: Seq[BinaryOperator] = Seq.empty
  def binaryInfixOperators: Seq[BinaryOperator] = Seq(Plus, Minus, Times, Divided, Power)
  def constants(): Seq[Constant] = Seq(e, pi)
}

sealed abstract class DoubleUnitaryOperator(val symbol:Symbol, val apply:(Double => Double)) extends UnitaryOperator[Double]
case object Neg extends DoubleUnitaryOperator('-, -_)
case object Sin extends DoubleUnitaryOperator('sin, Math.sin)
case object Cos extends DoubleUnitaryOperator('cos, Math.cos)
case object Tan extends DoubleUnitaryOperator('tan, Math.tan)
case object Asin extends DoubleUnitaryOperator('asin, Math.asin)
case object Acos extends DoubleUnitaryOperator('acos, Math.acos)
case object Atan extends DoubleUnitaryOperator('atan, Math.atan)
case object Sinh extends DoubleUnitaryOperator('sinh, Math.sinh)
case object Cosh extends DoubleUnitaryOperator('cosh, Math.cosh)
case object Tanh extends DoubleUnitaryOperator('tanh, Math.tanh)
case object Exp extends DoubleUnitaryOperator('exp, Math.exp)
case object Log extends DoubleUnitaryOperator('log, Math.log)

sealed abstract class DoubleBinaryOperator(val symbol:Symbol, val apply:((Double, Double) => Double)) extends BinaryOperator[Double]
case object Plus extends DoubleBinaryOperator('+, _ + _)
case object Minus extends DoubleBinaryOperator('-, _ - _)
case object Times extends DoubleBinaryOperator('*, _ * _)
case object Divided extends DoubleBinaryOperator('/, _ / _)
case object Power extends DoubleBinaryOperator('^, Math.pow)

abstract class DoubleConstant(val symbol:Symbol, val value: Double) extends Constant[Double]
case object e extends DoubleConstant('e, Math.E)
case object pi extends DoubleConstant('pi, Math.PI)
