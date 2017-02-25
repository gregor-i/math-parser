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

import mathParser.AbstractSyntaxTree._
import mathParser.{Compile, Variable}

import scala.util.Try

object DoubleCompile extends Compile[DoubleLanguage.type] {
  override def apply(node: Node[DoubleLanguage.type])(v1: Variable): Try[Double => Double] =
    Try {
      def functionString(node: Node[DoubleLanguage.type]): String = node match {
        case Constant(v) => v.toString
        case UnitaryNode(Neg, child) => s"-(${functionString(child)})"
        case UnitaryNode(Sin, child) => s"Math.sin(${functionString(child)})"
        case UnitaryNode(Cos, child) => s"Math.cos(${functionString(child)})"
        case UnitaryNode(Tan, child) => s"Math.tan(${functionString(child)})"
        case UnitaryNode(Asin, child) => s"Math.asin(${functionString(child)})"
        case UnitaryNode(Acos, child) => s"Math.acos(${functionString(child)})"
        case UnitaryNode(Atan, child) => s"Math.atan(${functionString(child)})"
        case UnitaryNode(Sinh, child) => s"Math.sinh(${functionString(child)})"
        case UnitaryNode(Cosh, child) => s"Math.cosh(${functionString(child)})"
        case UnitaryNode(Tanh, child) => s"Math.tanh(${functionString(child)})"
        case UnitaryNode(Exp, child) => s"Math.exp(${functionString(child)})"
        case UnitaryNode(Log, child) => s"Math.log(${functionString(child)})"

        case BinaryNode(Plus, left, right) => s"((${functionString(left)}) + (${functionString(right)}))"
        case BinaryNode(Minus, left, right) => s"((${functionString(left)}) - (${functionString(right)}))"
        case BinaryNode(Times, left, right) => s"((${functionString(left)}) * (${functionString(right)}))"
        case BinaryNode(Divided, left, right) => s"((${functionString(left)}) / (${functionString(right)}))"
        case BinaryNode(Power, left, right) => s"Math.pow(${functionString(left)}, ${functionString(right)})"

        case Variable(name) => name.name
      }

      import reflect.runtime.currentMirror
      import tools.reflect.ToolBox
      val toolbox = currentMirror.mkToolBox()
      val code =
        s"""new Function1[Double, Double]{
           |  def apply(${v1.name}:Double) = ${functionString(node)}
           |}
      """.stripMargin

      toolbox.compile(toolbox.parse(code))().asInstanceOf[Double => Double]
    }
}
