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

package mathParser.boolean

import mathParser.{BinaryOperator, Constant, UnitaryOperator}

sealed abstract class BooleanUnitaryOperator(val name:Symbol, val apply:(Boolean => Boolean)) extends UnitaryOperator[Boolean]
case object Not extends BooleanUnitaryOperator('!, !_)

sealed abstract class BooleanBinaryOperator(val name:Symbol, val apply:((Boolean, Boolean)=> Boolean)) extends BinaryOperator[Boolean]
case object And extends BooleanBinaryOperator('&, _ && _)
case object Or extends BooleanBinaryOperator('|, _ || _)
case object Equals extends BooleanBinaryOperator('=, _ == _)
case object Unequals extends BooleanBinaryOperator('!=, _ != _)

abstract class BooleanConstant(val name:Symbol, val apply: Boolean) extends Constant[Boolean]
case object `true` extends BooleanConstant('true, true)
case object `false` extends BooleanConstant('false, false)