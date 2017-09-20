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

import mathParser.{Language, LiteralParser}

import scala.util.Try

object DoubleLanguage extends Language{
  override type Skalar = Double
  override type Constant = DoubleConstant
  override type UnitaryOperator = DoubleUnitaryOperator
  override type BinaryOperator = DoubleBinaryOperator

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log)

  override def binaryOperators: Seq[BinaryOperator] = Seq.empty

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(Plus, Minus, Times, Divided, Power)

  override def constants(): Seq[Constant] = Seq(e, pi)

  val literalParser = new LiteralParser[this.type]{
    def tryToParse(s:String): Option[Double] = Try(s.toDouble).toOption
  }
}

trait DoubleSyntaxSugar {
  import mathParser.AbstractSyntaxTree

  type Node = AbstractSyntaxTree.Node[DoubleLanguage.type]
  type UnitaryNode = AbstractSyntaxTree.UnitaryNode[DoubleLanguage.type]
  def UnitaryNode(op:DoubleLanguage.UnitaryOperator, t:Node) = AbstractSyntaxTree.UnitaryNode[DoubleLanguage.type](op, t)
  type BinaryNode = AbstractSyntaxTree.BinaryNode[DoubleLanguage.type]
  def BinaryNode(op:DoubleLanguage.BinaryOperator, t1:Node, t2:Node) = AbstractSyntaxTree.BinaryNode[DoubleLanguage.type](op, t1, t2)
  type Constant = AbstractSyntaxTree.Constant[DoubleLanguage.type]

  def neg(t:Node): Node = UnitaryNode(Neg, t)
  def sin(t:Node): Node = UnitaryNode(Sin, t)
  def cos(t:Node): Node = UnitaryNode(Cos, t)
  def tan(t:Node): Node = UnitaryNode(Tan, t)
  def asin(t:Node): Node = UnitaryNode(Asin, t)
  def acos(t:Node): Node = UnitaryNode(Acos, t)
  def atan(t:Node): Node = UnitaryNode(Atan, t)
  def sinh(t:Node): Node = UnitaryNode(Sinh, t)
  def cosh(t:Node): Node = UnitaryNode(Cosh, t)
  def tanh(t:Node): Node = UnitaryNode(Tanh, t)
  def exp(t:Node): Node = UnitaryNode(Exp, t)
  def log(t:Node): Node = UnitaryNode(Log, t)

  def plus(t1:Node, t2:Node) = BinaryNode(Plus, t1, t2)
  def minus(t1:Node, t2:Node) = BinaryNode(Minus, t1, t2)
  def times(t1:Node, t2:Node) = BinaryNode(Times, t1, t2)
  def divided(t1:Node, t2:Node) = BinaryNode(Divided, t1, t2)
  def power(t1:Node, t2:Node) = BinaryNode(Power, t1, t2)

  def constant(v:DoubleLanguage.Skalar) = AbstractSyntaxTree.Constant[DoubleLanguage.type](v)
}
