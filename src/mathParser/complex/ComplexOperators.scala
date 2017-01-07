/*
 * Copyright (C) 2016  Gregor Ihmor
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

package mathParser.complex

import mathParser.{BinaryOperator, Constant, UnitaryOperator}
import spire.math.Complex
import spire.implicits._
import spire.syntax._

sealed abstract class ComplexUnitaryOperator(val name:Symbol, val apply:(Complex[Double] => Complex[Double])) extends UnitaryOperator[Complex[Double]]
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

sealed abstract class ComplexBinaryOperator(val name:Symbol, val apply:((Complex[Double], Complex[Double])=> Complex[Double])) extends BinaryOperator[Complex[Double]]
case object Plus extends ComplexBinaryOperator('+, _ + _)
case object Minus extends ComplexBinaryOperator('-, _ - _)
case object Times extends ComplexBinaryOperator('*, _ * _)
case object Divided extends ComplexBinaryOperator('/, _ / _)
case object Power extends ComplexBinaryOperator('^, _ ** _)

abstract class ComplexConstant(val name:Symbol, val apply: Complex[Double]) extends Constant[Complex[Double]]
case object e extends ComplexConstant('e, Complex(Math.E, 0))
case object pi extends ComplexConstant('pi, Complex(Math.PI, 0))
case object i extends ComplexConstant('i, Complex(0, 1))