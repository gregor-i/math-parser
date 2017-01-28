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

import mathParser.AbstractSyntaxTree._

object RepaceConstants {
  def apply[Lang <: Language](node: Node[Lang]): Node[Lang] = {
    def mayBeConstant(node: Node[Lang]): Boolean = node match {
      case Variable(name)             => false
      case UnitaryNode(_, child)      => mayBeConstant(child)
      case BinaryNode(_, left, right) => mayBeConstant(left) && mayBeConstant(right)
      case _                          => true
    }

    def replace(node: Node[Lang]): Node[Lang] =
      if (mayBeConstant(node))
        Constant(Evaluate(node)(PartialFunction.empty))
      else node match {
        case u@UnitaryNode(_, child)      => u.copy(child = replace(child))
        case b@BinaryNode(_, left, right) => b.copy(childLeft = replace(left), childRight = replace(right))
        case _                            => node
      }

    replace(node)
  }
}