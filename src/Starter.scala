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

import deriving.Derive
import operators.{ComplexOperators, DoubleOperators, Operators, Variables}
import spire.math.Complex

class VariablesX[A, Ops <: Operators[A]](implicit operators: Ops) extends Variables[A, Ops] {
  val x:Ops#Variable = new operators.Variable("x")
  val variables: Seq[Ops#Variable] = Seq(x)
}

object Starter {
  def exampleDoubles() = {
    import implicits.doubleHasOperators
    val p = parser.parser[Double, Operators[Double]]
    val t = p.apply("2^-x", new VariablesX[Double, Operators[Double]])
    t.foreach { term =>
      val d = term.apply {
        case "x" => 25
      }
      println(d)
    }
    println(t)
  }

  def exampleBooleans() = {
    import implicits.booleanHasOperators
    val p = parser.parser[Boolean, Operators[Boolean]]
    println(p("!false", Variables.emptyVariables))
  }

  def exampleComplex() = {
    import implicits.spireComplexHasOperators
    import spire.implicits._
    import spire.math.Complex
    val p = parser.parser[Complex[Double], ComplexOperators[Double]]
    println(p("sin(25 + 3*i)", Variables.emptyVariables))
  }

  def exampleDerive() = {
    import implicits.spireComplexHasOperators
    import spire.implicits._
    import spire.math.Complex

    import implicits._

    type A = Complex[Double]
    type Ops = ComplexOperators[Double]

    val p = parser.parser[A, Ops]
    var vars = new VariablesX[A, Ops]
    val t = p("sin(x) + 23 * x", vars)

    val d = Derive[A, Ops](t.get)(vars.x)
  }

  def main(args: Array[String]): Unit = {
    exampleDoubles()
    exampleBooleans()
    exampleComplex()
  }
}
