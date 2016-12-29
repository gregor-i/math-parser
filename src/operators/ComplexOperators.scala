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

import deriving.Derive
import spire.algebra.{Field, IsReal, NRoot, Trig}
import spire.math.Complex

import scala.util.Try

class ComplexOperators[A : Field : Trig : NRoot : IsReal]() extends Operators[Complex[A]] with Derive[Complex[A]] {
  ops =>
  val field: Field[A] = implicitly

  def fromDouble(d:Double) = Complex(field.fromDouble(d), field.zero)

  object Nodes {
    def neg(t: Term) = new UnitaryNode(Operators.neg, t)
    def cos(t: Term) = new UnitaryNode(Operators.cos, t)
    def sin(t: Term) = new UnitaryNode(Operators.sin, t)
    def log(t: Term) = new UnitaryNode(Operators.log, t)
    def times(a: Term, b:Term) = new BinaryNode(Operators.times, a, b)
    def plus(a: Term, b:Term) = new BinaryNode(Operators.plus, a, b)
    def minus(a: Term, b:Term) = new BinaryNode(Operators.minus, a, b)
    def divided(a: Term, b:Term) = new BinaryNode(Operators.divided, a, b)
    def power(a: Term, b:Term) = new BinaryNode(Operators.power, a, b)
    def literal(d: Double): Term = new Literal(Complex(field.fromDouble(d), field.fromDouble(0)))
  }

  object Operators {
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
    val unitary = Seq(neg, sin, cos, tan, asin, acos, atan, sinh, cosh, tanh, exp, log)

    val plus = new BinaryOperator("+", _ + _)
    val minus = new BinaryOperator("-", _ - _)
    val times = new BinaryOperator("*", _ * _)
    val divided = new BinaryOperator("/", _ / _)
    val power = new BinaryOperator("^", _ ** _)
    val binaryInfix = Seq(plus, minus, times, divided, power)
  }

  object Constants {
    val e = Constant("e", fromDouble(Math.E))
    val pi = Constant("pi", fromDouble(Math.PI))
    val i = Constant("i", Complex(field.zero, field.one))
    def apply() = Seq(e, pi, i)
  }

  override def parseLiteral(input: String): Option[Literal] = Try(input.toDouble).map(fromDouble).map(Literal).toOption

  override def constants(): Seq[Constant] = Constants()

  override def binaryOperators: Seq[BinaryOperator] = Seq()

  override def binaryInfixOperators: Seq[BinaryOperator] = Operators.binaryInfix

  override def unitaryOperators: Seq[UnitaryOperator] = Operators.unitary

  override def derive(term: Term)(variable: Variable): Term = {
    import Nodes._

    println("deriving "+term)

    term match {
      case `variable` => literal(1d)
      case Variable(_) => literal(0d)
      case Literal(_) => literal(0d)
      case Constant(_, _) => literal(0d)
      case UnitaryNode(op, t) =>
        val abl = derive(t)(variable)
        op match {
          case Operators.neg => neg(abl)
          case Operators.sin => times(abl, cos(t))
          case Operators.cos => times(abl, neg(sin(t)))
          case _ => ??? // todo
        }
      case BinaryNode(op, t1, t2) =>
        val abl1 = derive(t1)(variable)
        val abl2 = derive(t2)(variable)
        println(for(o <- Operators.binaryInfix)
          yield o.getClass)
        println(op.getClass)
        op match {
          case Operators.plus => plus(abl1, abl2)
          case Operators.minus => minus(abl1, abl2)
          case Operators.times => plus(times(abl1, t2), times(abl2, t1))
          case Operators.divided => divided(minus(times(t1, abl2), times(t2, abl1)), times(t2, t2))
          case Operators.power =>
            // d/dx(f(x)^(g(x))) = f(x)^(g(x) - 1) (g(x) f'(x) + f(x) log(f(x)) g'(x))
            val one = power(t1, minus(t2, literal(1))) // f(x)^(g(x) - 1)
          val two = times(t2, abl1) // g(x) f'(x)
          val three = times(times(t1, log(t1)), abl2) // f(x) log(f(x)) g'(x))
            times(one, plus(two, three))
          case x => println("no match for "+x)
            ???
        }
      case x => println("no match for "+x)
        ???
    }
  }
}