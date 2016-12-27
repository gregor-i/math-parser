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

package operators

import scala.util.Try

object BooleanOperators extends Operators[Boolean] {
  val not = new UnitaryOperator("!", !_)

  val and = new BinaryOperator("&", _ && _)
  val or = new BinaryOperator("|", _ || _)
  val equals = new BinaryOperator("|", _ == _)
  val unequals = new BinaryOperator("|", _ != _)

  val `true` = new Constant("true", true)
  val `false` = new Constant("false", false)

  override def parseLiteral(input: String): Option[Literal] = Try{input.toBoolean}.map(Literal).toOption

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(not)

  override def constants(): Seq[Constant] = Seq(`true`, `false`)

  override def binaryOperators: Seq[BinaryOperator] = Seq.empty

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(and, or, equals, unequals)
}
