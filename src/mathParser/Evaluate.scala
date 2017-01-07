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

package mathParser

import mathParser.AbstractSyntaxTree._

object Evaluate {
  def apply[Lang <: Language](node: Node[Lang])
                             (variableAssignment: PartialFunction[Symbol, Lang#Skalar]): Lang#Skalar = {

    def evalUnitaryNode(node: UnitaryNode[Lang]): Lang#Skalar = {
      val op = node.op.asInstanceOf[UnitaryOperator[Lang#Skalar]]
      op.apply(eval(node.child))
    }

    def evalBinaryNode(node: BinaryNode[Lang]): Lang#Skalar = {
      val op = node.op.asInstanceOf[BinaryOperator[Lang#Skalar]]
      op.apply(eval(node.childLeft), eval(node.childRight))
    }

    def eval(node: Node[Lang]): Lang#Skalar = node match {
      case Constant(value) => value
      case un : UnitaryNode[Lang] => evalUnitaryNode(un)
      case bn : BinaryNode[Lang] => evalBinaryNode(bn)
      case Variable(name) => variableAssignment(name)
    }

    eval(node)
  }
}
