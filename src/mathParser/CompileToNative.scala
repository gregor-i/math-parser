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

import java.util.Random

import mathParser.AbstractSyntaxTree._
import mathParser.double.DoubleLanguage
import net.openhft.compiler.CompilerUtils

import scala.util.Try

object CompileToNative {

  def function1(node: Node[DoubleLanguage.type], v1: Variable): Try[Function1[Double, Double]] =
    Try {
      def functionString(node:Node[DoubleLanguage.type]): String =  node match {
        case Constant(v) => v.toString
        case UnitaryNode(double.Neg, child) => s"-(${functionString(child)})"
        case UnitaryNode(double.Sin, child) => s"Math.sin(${functionString(child)})"
        case UnitaryNode(double.Cos, child) => s"Math.cos(${functionString(child)})"
        case UnitaryNode(double.Tan, child) => s"Math.tan(${functionString(child)})"
        case UnitaryNode(double.Asin, child) => s"Math.asin(${functionString(child)})"
        case UnitaryNode(double.Acos, child) => s"Math.acos(${functionString(child)})"
        case UnitaryNode(double.Atan, child) => s"Math.atan(${functionString(child)})"
        case UnitaryNode(double.Sinh, child) => s"Math.sinh(${functionString(child)})"
        case UnitaryNode(double.Cosh, child) => s"Math.cosh(${functionString(child)})"
        case UnitaryNode(double.Tanh, child) => s"Math.tanh(${functionString(child)})"
        case UnitaryNode(double.Exp, child) => s"Math.exp(${functionString(child)})"
        case UnitaryNode(double.Log, child) => s"Math.log(${functionString(child)})"

        case BinaryNode(double.Plus, left, right) => s"((${functionString(left)})+(${functionString(right)}))"
        case BinaryNode(double.Minus, left, right) => s"((${functionString(left)})-(${functionString(right)}))"
        case BinaryNode(double.Times, left, right) => s"((${functionString(left)})*(${functionString(right)}))"
        case BinaryNode(double.Divided, left, right) => s"((${functionString(left)})/(${functionString(right)}))"
        case BinaryNode(double.Power, left, right) => s"Math.pow(${functionString(left)}, ${functionString(right)})"

        case Variable(name) => name.name
      }

      val randomHash = new Random().nextInt().toString.replace('-', '0')
      val className = s"mathParser.compiledAtRuntime.Class$randomHash"

      val javaCode =
        s"""package mathParser.compiledAtRuntime;
           |
           |import java.util.function.DoubleFunction;
           |import java.lang.Math;
           |
           |public class Class$randomHash implements DoubleFunction<Double> {
           |@Override
           |  public Double apply(double ${v1.name}) {
           |    return ${functionString(node)};
           |  }
           |}
           |
       """.stripMargin

      val c = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode)
      val javaFunction = c.newInstance().asInstanceOf[java.util.function.DoubleFunction[Double]]
      d => javaFunction(d)
    }

}
