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

package mathParser.complex

import mathParser.AbstractSyntaxTree._
import mathParser.{Compile, Variable}
import spire.math.Complex

import scala.annotation.tailrec
import scala.util.Try

object ComplexCompile extends Compile[ComplexLanguage.type] {
  override def apply(node: Node[ComplexLanguage.type])(v1: Variable): Try[Complex[Double] => Complex[Double]] =
    Try {
      def functionString(node: Node[ComplexLanguage.type]): String = node match {
        case Constant(v) => s"Complex[Double](${v.real}, ${v.imag})"
        case UnitaryNode(Neg, child) => s"-(${functionString(child)})"
        case UnitaryNode(Sin, child) => s"(${functionString(child)}).sin"
        case UnitaryNode(Cos, child) => s"(${functionString(child)}).cos"
        case UnitaryNode(Tan, child) => s"(${functionString(child)}).tan"
        case UnitaryNode(Asin, child) => s"(${functionString(child)}).asin"
        case UnitaryNode(Acos, child) => s"(${functionString(child)}).acos"
        case UnitaryNode(Atan, child) => s"(${functionString(child)}).atan"
        case UnitaryNode(Sinh, child) => s"(${functionString(child)}).sinh"
        case UnitaryNode(Cosh, child) => s"(${functionString(child)}).cosh"
        case UnitaryNode(Tanh, child) => s"(${functionString(child)}).tanh"
        case UnitaryNode(Exp, child) => s"(${functionString(child)}).exp"
        case UnitaryNode(Log, child) => s"(${functionString(child)}).log"

        case BinaryNode(Plus, left, right) => s"((${functionString(left)}) + (${functionString(right)}))"
        case BinaryNode(Minus, left, right) => s"((${functionString(left)}) - (${functionString(right)}))"
        case BinaryNode(Times, left, right) => s"((${functionString(left)}) * (${functionString(right)}))"
        case BinaryNode(Divided, left, right) => s"((${functionString(left)}) / (${functionString(right)}))"
        case BinaryNode(Power, left, right) => s"((${functionString(left)}) ** (${functionString(right)}))"

        case Variable(name) => name.name
      }

      import reflect.runtime.currentMirror
      import tools.reflect.ToolBox
      val toolbox = currentMirror.mkToolBox()
      val code =
        s"""import spire.implicits.DoubleAlgebra
           |import spire.math.Complex
           |
           |new Function1[Complex[Double], Complex[Double]]{
           |  def apply(${v1.name}:Complex[Double]):Complex[Double] = ${functionString(node)}
           |}
      """.stripMargin

      toolbox.compile(toolbox.parse(code))().asInstanceOf[Complex[Double] => Complex[Double]]
    }
}
