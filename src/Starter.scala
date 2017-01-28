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

import mathParser.complex.ComplexDerive
import mathParser.{Evaluate, Parser}
import mathParser.double.{DoubleDerive, DoubleLanguage}

object Starter {
  def exampleDoubles() = {
    println("exampleDoubles")
    import mathParser.implicits.doubleParseLiterals

    val lang = DoubleLanguage
    val xVariable = Set('x)
    val parser = Parser(lang, xVariable)
    val t = parser("2^-x+e")
    val e = t.map{ term =>
      Evaluate(term){
        case 'x => 25
      }
    }
    println(t -> e)
  }

  def exampleDoublesDerive() = {
    println("exampleDoublesDerive")
    import mathParser.implicits.doubleParseLiterals

    val lang = DoubleLanguage
    val xVariable = Set('x)
    val parser = Parser(lang, xVariable)
    val t = parser("2^-x")
    t.fold{
      println("input could not be parsed")
    }{ term =>
         println(DoubleDerive(term)('x))
    }
    println(t)
  }

  def exampleBooleans() = {
    println("exampleBooleans")
    import mathParser.implicits.booleanParseLiterals
    val lang = mathParser.boolean.BooleanLanguage
    val parser = Parser(lang, Set.empty)
    println(parser("!false"))
  }

  def exampleComplex() = {
    println("exampleComplex")
    import mathParser.implicits.complexParseLiterals
    val lang = mathParser.complex.ComplexLanguage
    val parser = Parser(lang, Set.empty)
    println(parser("sin(25 + 3*i)"))
  }

  def exampleDerive(s:String) = {
    println("exampleDerive")
    import mathParser.implicits.complexParseLiterals

    val lang = mathParser.complex.ComplexLanguage
    val parser = Parser(lang, Set('x))

    val t = parser(s)

    val d = t.map(t => ComplexDerive(t)('x))

    println(t -> d)
  }


  def main(args: Array[String]): Unit = {
    exampleDoubles()
    exampleDoublesDerive()
    exampleBooleans()
    exampleComplex()
    exampleDerive("5*x")
  }
}
