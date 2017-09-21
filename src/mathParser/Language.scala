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

package mathParser

trait Language {
  type Skalar
  type Constant <: mathParser.Constant[Skalar]
  type UnitaryOperator <: mathParser.UnitaryOperator[Skalar]
  type BinaryOperator <: mathParser.BinaryOperator[Skalar]

  def unitaryOperators: Seq[UnitaryOperator]
  def binaryOperators: Seq[BinaryOperator]
  def binaryInfixOperators: Seq[BinaryOperator]
  def constants(): Seq[Constant]
  
  sealed trait Node
  case class Constant(value:Skalar) extends Node
  case class UnitaryNode(op:UnitaryOperator, child:Node) extends Node
  case class BinaryNode(op:BinaryOperator, childLeft:Node, childRight:Node) extends Node
  case class Variable(name: Symbol) extends Node
}
