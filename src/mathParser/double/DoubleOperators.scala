/*
 * Copyright (C) 2017  Gregor Ihmor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package mathParser.double

import mathParser.{BinaryOperator, Constant, UnitaryOperator}

sealed abstract class DoubleUnitaryOperator(val name:Symbol, val apply:(Double => Double)) extends UnitaryOperator[Double]
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

sealed abstract class DoubleBinaryOperator(val name:Symbol, val apply:((Double, Double)=> Double)) extends BinaryOperator[Double]
case object Plus extends DoubleBinaryOperator('+, _ + _)
case object Minus extends DoubleBinaryOperator('-, _ - _)
case object Times extends DoubleBinaryOperator('*, _ * _)
case object Divided extends DoubleBinaryOperator('/, _ / _)
case object Power extends DoubleBinaryOperator('^, Math.pow)

abstract class DoubleConstant(val name:Symbol, val apply: Double) extends Constant[Double]
case object e extends DoubleConstant('e, Math.E)
case object pi extends DoubleConstant('pi, Math.PI)