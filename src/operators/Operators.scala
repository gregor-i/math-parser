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

trait Operators[Skalar] {
  case class UnitaryOperator(name: String, apply: Skalar => Skalar)
  case class BinaryOperator (name: String, apply: (Skalar, Skalar) => Skalar)

  sealed trait Term {
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar
    def isVariable(variableName:String): Boolean
  }

  case class Variable(name:String) extends Term{
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = variableValues(name)
    def isVariable(variableName:String): Boolean = name == variableName
  }

  case class Literal(value: Skalar) extends Term{
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = value
    def isVariable(variableName:String): Boolean = false
  }

  case class Constant(name: String, value: Skalar) extends Term{
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = value
    def isVariable(variableName:String): Boolean = false
  }

  case class UnitaryNode(op: UnitaryOperator, para1: Term) extends Term {
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = op.apply(para1.apply(variableValues))
    def isVariable(variableName:String): Boolean = para1.isVariable(variableName)
    override def toString: String = "%s(%s)".format(op.name, para1)
  }

  case class BinaryNode(op: BinaryOperator, para1: Term, para2: Term) extends Term {
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = op.apply(para1.apply(variableValues), para2.apply(variableValues))
    def isVariable(variableName:String): Boolean = para1.isVariable(variableName) || para2.isVariable(variableName)
    override def toString: String = "%s(%s, %s)".format(op.name, para1, para2)
  }

  def unitaryOperators: Seq[UnitaryOperator]
  def binaryOperators: Seq[BinaryOperator]
  def binaryInfixOperators: Seq[BinaryOperator]
  def constants(): Seq[Constant]
  def parseLiteral(input: String): Option[Literal]
}