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

package mathParser.boolean

import mathParser.Language

object BooleanLanguage extends Language{
  override type Skalar = Boolean
  override type Constant = BooleanConstant
  override type UnitaryOperator = BooleanUnitaryOperator
  override type BinaryOperator = BooleanBinaryOperator

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(Not)

  override def binaryOperators: Seq[BinaryOperator] = Seq.empty

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(And, Or, Equals, Unequals)

  override def constants(): Seq[Constant] = Seq(`true`, `false`)
}
