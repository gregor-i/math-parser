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

object DoubleOperators extends Operators[Double] {
  val neg = UnitaryOperator("-", -_)
  val sin = UnitaryOperator("sin", Math.sin)
  val cos = UnitaryOperator("cos", Math.cos)
  val tan = UnitaryOperator("tan", Math.tan)
  val asin = UnitaryOperator("asin", Math.asin)
  val acos = UnitaryOperator("acos", Math.acos)
  val atan = UnitaryOperator("atan", Math.atan)
  val sinh = UnitaryOperator("sinh", Math.sinh)
  val cosh = UnitaryOperator("cosh", Math.cosh)
  val tanh = UnitaryOperator("tanh", Math.tanh)
  val exp = UnitaryOperator("exp", Math.exp)
  val log = UnitaryOperator("log", Math.log)

  val plus = BinaryOperator("+", _ + _)
  val minus = BinaryOperator("-", _ - _)
  val times = BinaryOperator("*", _ * _)
  val divided = BinaryOperator("/", _ / _)
  val power = BinaryOperator("^", Math.pow)

  val e = Constant("e", Math.E)
  val pi = Constant("pi", Math.PI)

  override def parseLiteral(input: String): Option[Literal] = Try(input.toDouble).map(Literal).toOption

  override def constants(): Seq[Constant] = Seq(e, pi)

  override def binaryOperators: Seq[BinaryOperator] = Seq()

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(plus, minus, times, divided, power)

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(neg, sin, cos, tan, asin, acos, atan, sinh, cosh, tanh, exp, log)
}