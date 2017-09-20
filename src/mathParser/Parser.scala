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

object Parser {
  implicit final class SymbolsCompareToStrings(val sy:Symbol) extends AnyVal{
    @inline def === (st:String):Boolean = sy.name == st
  }

  implicit final class CharsCompare(val c1:Char) extends AnyVal{
    @inline def === (c2:Char):Boolean = c1 == c2
  }

  private def collectSomes[A]:PartialFunction[Option[A], A] = { case Some(x) => x }
}

case class Parser[Lang <: Language](lang:Lang, variables:VariableSet, literalParser: LiteralParser[Lang]) {
  import Parser._


  private def parseTerm(_input: String): Option[Node[Lang]] = {
    val input = _input.trim()

    def parse_cons: Option[Node[Lang]] = lang.constants().find(_.name === input).map(constant => Constant(constant.apply))
    def parse_vars: Option[Node[Lang]] = variables.find(_ === input).map(Variable.apply)
    def parse_literal: Option[Node[Lang]] = literalParser.tryToParse(input).map(literal => Constant(literal))
    def parse_klammern: Option[Node[Lang]] =
      if (input.head === '(' && input.last === ')')
        parseTerm(input.tail.init)
      else
        None

    def parse_op_bin_infix: Option[Node[Lang]] = lang.binaryInfixOperators
      .map { op => parseBinaryNode(input, op.name.name.head, BinaryNode(op, _, _)) }
      .collectFirst(collectSomes)

    def parse_op_bin_prefix: Option[Node[Lang]] = lang.binaryOperators
      .filter(op => input.matches(s"${op.name}\\(.*\\)"))
      .map { op => parseBinaryNode(input.substring(op.name.name.length + 1, input.length - 1), ',', BinaryNode(op, _, _)) }
      .collectFirst(collectSomes)

    def parse_op_uni_prefix: Option[Node[Lang]] = lang.unitaryOperators
      .filter { op => input.startsWith(op.name.name) }
      .map(op => parseTerm(input.drop(op.name.name.length)).map(UnitaryNode(op, _)))
      .collectFirst(collectSomes)

    if (input.nonEmpty)
      parse_cons orElse
        parse_vars orElse
        parse_literal orElse
        parse_klammern orElse
        parse_op_uni_prefix orElse
        parse_op_bin_prefix orElse
        parse_op_bin_infix
    else None
  }


  private def parseBinaryNode(input: String, splitter: Char, f: (Node[Lang], Node[Lang]) => Node[Lang])
                             : Option[Node[Lang]] = {
    def splitByRegardingParenthesis(input:String, splitter:Char): Option[(String, String)] = {
      var k = -1
      var c = 0
      input.zipWithIndex.foreach {
        case (')', _) => c = c - 1
        case ('(', _) => c = c + 1
        case (`splitter`, i) => if (c == 0) k = i
        case _ =>
      }
      if (c != 0 || k == -1) None
      else Some(input.take(k), input.drop(k+1))
    }

    for {
      (sub1, sub2) <- splitByRegardingParenthesis(input.trim, splitter)
      p1 <- parseTerm(sub1)
      p2 <- parseTerm(sub2)
    } yield f(p1, p2)
  }

  def apply(input: String): Option[Node[Lang]] =
    parseTerm(input)
}
