package mathParser.complex

import mathParser.{BinaryOperator, Constant, UnitaryOperator}
import spire.implicits._
import spire.math.Complex

sealed abstract class ComplexUnitaryOperator(val name:Symbol, val apply:(C => C)) extends UnitaryOperator[C]
case object Neg extends ComplexUnitaryOperator('-, -_)
case object Sin extends ComplexUnitaryOperator('sin, _.sin)
case object Cos extends ComplexUnitaryOperator('cos, _.cos)
case object Tan extends ComplexUnitaryOperator('tan, _.tan)
case object Asin extends ComplexUnitaryOperator('asin, _.asin)
case object Acos extends ComplexUnitaryOperator('acos, _.acos)
case object Atan extends ComplexUnitaryOperator('atan, _.atan)
case object Sinh extends ComplexUnitaryOperator('sinh, _.sinh)
case object Cosh extends ComplexUnitaryOperator('cosh, _.cosh)
case object Tanh extends ComplexUnitaryOperator('tanh, _.tanh)
case object Exp extends ComplexUnitaryOperator('exp, _.exp)
case object Log extends ComplexUnitaryOperator('log, _.log)

sealed abstract class ComplexBinaryOperator(val name:Symbol, val apply:((C, C)=> C)) extends BinaryOperator[C]
case object Plus extends ComplexBinaryOperator('+, _ + _)
case object Minus extends ComplexBinaryOperator('-, _ - _)
case object Times extends ComplexBinaryOperator('*, _ * _)
case object Divided extends ComplexBinaryOperator('/, _ / _)
case object Power extends ComplexBinaryOperator('^, _ ** _)

abstract class ComplexConstant(val name:Symbol, val apply: C) extends Constant[C]
case object e extends ComplexConstant('e, Complex(Math.E, 0))
case object pi extends ComplexConstant('pi, Complex(Math.PI, 0))
case object i extends ComplexConstant('i, Complex(0, 1))
