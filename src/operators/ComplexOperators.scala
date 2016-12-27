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

import spire.algebra.{Field, IsReal, NRoot, Trig}
import spire.math.Complex

import scala.util.Try

class ComplexOperators[A : Field : Trig : NRoot : IsReal]() extends Operators[Complex[A]] {
  val field: Field[A] = implicitly

  def fromDouble(d:Double) = Complex(field.fromDouble(d), field.zero)

  val neg = new UnitaryOperator("-", -_)
  val sin = new UnitaryOperator("sin", _.sin)
  val cos = new UnitaryOperator("cos", _.cos)
  val tan = new UnitaryOperator("tan", _.tan)
  val asin = new UnitaryOperator("asin", _.asin)
  val acos = new UnitaryOperator("acos", _.acos)
  val atan = new UnitaryOperator("atan", _.atan)
  val sinh = new UnitaryOperator("sinh", _.sinh)
  val cosh = new UnitaryOperator("cosh", _.cosh)
  val tanh = new UnitaryOperator("tanh", _.tanh)
  val exp = new UnitaryOperator("exp", _.exp)
  val log = new UnitaryOperator("log", _.log)

  val plus = new BinaryOperator("+", _ + _)
  val minus = new BinaryOperator("-", _ - _)
  val times = new BinaryOperator("*", _ * _)
  val divided = new BinaryOperator("/", _ / _)
  val power = new BinaryOperator("^", _ ** _)

  val e = Constant("e", fromDouble(Math.E))
  val pi = Constant("pi", fromDouble(Math.PI))
  val i = Constant("i", Complex(field.zero, field.one))

  override def parseLiteral(input: String): Option[Literal] = Try(input.toDouble).map(fromDouble).map(Literal).toOption

  override def constants(): Seq[Constant] = Seq(e, pi, i)

  override def binaryOperators: Seq[BinaryOperator] = Seq()

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(plus, minus, times, divided, power)

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(neg, sin, cos, tan, asin, acos, atan, sinh, cosh, tanh, exp, log)
}