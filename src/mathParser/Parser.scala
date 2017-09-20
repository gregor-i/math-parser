package mathParser

import mathParser.AbstractSyntaxTree._

case class Parser[S, Lang <: Language[S]](lang: Lang, literalParser: LiteralParser[S]) {

  @inline implicit final class SymbolsCompareToStrings(val sy: Symbol) {
    @inline def ===(st: String): Boolean = sy.name == st
  }

  def apply(input: String)(variables: VariableSet): Option[Node[S, Lang]] = {

    def binaryNode(input: String, splitter: Char,
                   f: (Node[S, Lang], Node[S, Lang]) => Node[S, Lang]): Option[Node[S, Lang]] = {
      def splitByRegardingParenthesis(input: String, splitter: Char): Option[(String, String)] = {
        var k = -1
        var c = 0
        input.zipWithIndex.foreach {
          case (')', _) => c = c - 1
          case ('(', _) => c = c + 1
          case (`splitter`, i) => if (c == 0) k = i
          case _ =>
        }
        if (c != 0 || k == -1) None
        else Some(input.take(k), input.drop(k + 1))
      }

      for {
        (sub1, sub2) <- splitByRegardingParenthesis(input.trim, splitter)
        p1 <- term(sub1)
        p2 <- term(sub2)
      } yield f(p1, p2)
    }

    def constant(input: String): Option[Node[S, Lang]] = lang.constants().find(_.name === input).map(constant => Constant(constant.apply))

    def variable(input: String): Option[Node[S, Lang]] = variables.find(_ === input).map(Variable.apply)

    def literal(input: String): Option[Node[S, Lang]] = literalParser.tryToParse(input).map(literal => Constant(literal))

    def parenthesis(input: String): Option[Node[S, Lang]] =
      if (input.startsWith("(") && input.endsWith(")"))
        term(input.tail.init)
      else
        None

    def binaryInfixOperation(input: String): Option[Node[S, Lang]] = lang.binaryInfixOperators
      .flatMap(op => binaryNode(input, op.name.name.head, BinaryNode(op, _, _)))
      .headOption

    def binaryPrefixOperation(input: String): Option[Node[S, Lang]] = lang.binaryOperators
      .filter(op => input.matches(s"${op.name}\\(.*\\)"))
      .flatMap(op => binaryNode(input.substring(op.name.name.length + 1, input.length - 1), ',', BinaryNode(op, _, _)))
      .headOption

    def unitaryPrefixOperation(input: String): Option[Node[S, Lang]] = lang.unitaryOperators
      .filter(op => input.startsWith(op.name.name))
      .flatMap(op => term(input.drop(op.name.name.length)).map(UnitaryNode(op, _)))
      .headOption

    def term(_input: String): Option[Node[S, Lang]] = {
      val input = _input.trim()

      constant(input) orElse
        variable(input) orElse
        literal(input) orElse
        parenthesis(input) orElse
        unitaryPrefixOperation(input) orElse
        binaryPrefixOperation(input) orElse
        binaryInfixOperation(input)
    }

    term(input)
  }
}
