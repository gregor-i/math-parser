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

import scala.util.Try

object ComplexCompile extends Compile[Lang] {

  import mathParser.ReflectionObjects._
  import universe.reify

  import scala.tools.reflect.Eval

  @deprecated("use scalaExpr instead.", "now")
  def scalaCode(node: Node[Lang]): String = node match {
    case Constant(v) => s"Complex[Double](${v.real}, ${v.imag})"
    case UnitaryNode(Neg, child) => s"-(${scalaCode(child)})"
    case UnitaryNode(Sin, child) => s"(${scalaCode(child)}).sin"
    case UnitaryNode(Cos, child) => s"(${scalaCode(child)}).cos"
    case UnitaryNode(Tan, child) => s"(${scalaCode(child)}).tan"
    case UnitaryNode(Asin, child) => s"(${scalaCode(child)}).asin"
    case UnitaryNode(Acos, child) => s"(${scalaCode(child)}).acos"
    case UnitaryNode(Atan, child) => s"(${scalaCode(child)}).atan"
    case UnitaryNode(Sinh, child) => s"(${scalaCode(child)}).sinh"
    case UnitaryNode(Cosh, child) => s"(${scalaCode(child)}).cosh"
    case UnitaryNode(Tanh, child) => s"(${scalaCode(child)}).tanh"
    case UnitaryNode(Exp, child) => s"(${scalaCode(child)}).exp"
    case UnitaryNode(Log, child) => s"(${scalaCode(child)}).log"

    case BinaryNode(Plus, left, right) => s"((${scalaCode(left)}) + (${scalaCode(right)}))"
    case BinaryNode(Minus, left, right) => s"((${scalaCode(left)}) - (${scalaCode(right)}))"
    case BinaryNode(Times, left, right) => s"((${scalaCode(left)}) * (${scalaCode(right)}))"
    case BinaryNode(Divided, left, right) => s"((${scalaCode(left)}) / (${scalaCode(right)}))"
    case BinaryNode(Power, left, right) => s"((${scalaCode(left)}) ** (${scalaCode(right)}))"

    case Variable(name) => name.name
  }

  def scalaExpr(node: Node[Lang],
                variables: Map[Symbol, Expr[C]]): Expr[C] = {
    def recursion(node: Node[Lang]): Expr[C] =
      node match {
        case Constant(v) => reify(v)

        case UnitaryNode(op, child) =>
          val r = recursion(child)
          reify(op.apply(r.splice))

        case BinaryNode(op, left, right) =>
          val l = recursion(left)
          val r = recursion(right)
          reify(op.apply(l.splice, r.splice))

        case Variable(name) => variables(name)
      }

    recursion(node)
  }

  def function1(node:Node[Lang], v1Symbol:Variable): Expr[C => C] =
    reify((v1: C) => scalaExpr(node, Map(v1Symbol -> reify(v1))).splice)

  def function2(node:Node[Lang], v1Symbol:Variable, v2Symbol:Variable): Expr[(C, C) => C] =
    reify((v1: C, v2:C) => scalaExpr(node, Map(v1Symbol -> reify(v1), v2Symbol -> reify(v2))).splice)

  def apply(node: Node[Lang]): Try[C] =
    Try(scalaExpr(node, Map()).eval)

  override def apply(node: Node[Lang], v1Symbol: Variable): Try[C => C] =
    Try(function1(node, v1Symbol).eval)

  def apply(node: Node[Lang], v1Symbol: Variable, v2Symbol: Variable): Try[(C, C) => C] =
    Try(function2(node, v1Symbol, v2Symbol).eval)
}
