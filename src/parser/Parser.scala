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

package parser

import operators.{PublicTerm, Variables, Operators}

class Parser[A](implicit operators: Operators[A]) {
  import operators._

  private def parseTerm(_input: String)(implicit variables: Variables[A]): Option[Term] = {
    val input = _input.trim()

    def parse_cons: Option[Term] = constants().find(_.name == input)
    def parse_vars: Option[Term] = variables.variables.find(_.name == input).map(_.asInstanceOf[Term])
    def parse_literal: Option[Term] = parseLiteral(input)
    def parse_klammern: Option[Term] =
      if (input.head == '(' && input.last == ')')
        parseTerm(input.tail.init)
      else
        None

    def parse_op_bin_infix: Option[Term] = binaryInfixOperators
      .map { op => parseBinaryNode(input, op.name.head, new BinaryNode(op.name, op, _, _)) }
      .find(_.isDefined).flatten

    def parse_op_bin_prefix: Option[Term] = binaryOperators
      .filter(op => input.matches(s"${op.name}\\(.*\\)"))
      .map { op => parseBinaryNode(input.substring(op.name.length + 1, input.length - 1), ',', new BinaryNode(op.name, op, _, _)) }
      .find(_.isDefined).flatten

    def parse_op_uni_prefix: Option[Term] = unitaryOperators
      .filter { op => input.startsWith(op.name) }
      .map(op => parseTerm(input.drop(op.name.length)).map(new UnitaryNode(op.name, op, _)))
      .find(_.isDefined).flatten

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


  private def parseBinaryNode(input: String, splitter: Char, f: (Term, Term) => BinaryNode)
                             (implicit variables: Variables[A]): Option[BinaryNode] = {
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

  def apply(input: String, variables: Variables[A]): Option[PublicTerm[A]] =
    parseTerm(input)(variables)
}