package mathParser

import cats.parse.Parser
import mathParser.Token
import mathParser.Token._

import scala.annotation.tailrec

object Parser {
  def parse[O, S, V](lang: Language[O, S, V], literalParser: LiteralParser[S])(
      input: String
  ): Option[AbstractSyntaxTree[O, S, V]] = {
    type TokenList = List[Token[O, S, V]]

    def binaryNode(
        input: TokenList,
        splitter: Token[O, S, V],
        f: (AbstractSyntaxTree[O, S, V], AbstractSyntaxTree[O, S, V]) => AbstractSyntaxTree[O, S, V]
    ): Option[AbstractSyntaxTree[O, S, V]] =
      for {
        (sub1, sub2) <- splitByRegardingParenthesis(input, splitter)
        p1           <- loop(sub1)
        p2           <- loop(sub2)
      } yield f(p1, p2)

    def constant(input: Token[O, S, V]): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case Scalar(s) => Some(AbstractSyntaxTree.ConstantNode(s))
        case _         => None
      }

    def variable(input: Token[O, S, V]): Option[AbstractSyntaxTree[O, S, V]] =
      lang.variables
        .find((_, vari) => Variable(vari) == input)
        .map(v => VariableNode(v._2))

    def parenthesis(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case OpenParens +: remaining :+ CloseParens => loop(remaining)
        case _                                      => None
      }

    def binaryInfixOperation(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      lang.binaryInfixOperators.view
        .flatMap((_, op) => binaryNode(input, Operator(op), BinaryNode[O, S, V](op, _, _)))
        .headOption

    def binaryPrefixOperation(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case Operator(head: BinaryOperator) :: (OpenParens +: remaining :+ CloseParens) =>
          binaryNode(remaining, Comma, BinaryNode[O, S, V](head, _, _))
        case _ => None
      }

    def unitaryPrefixOperation(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case Operator(head: UnitaryOperator) :: tail =>
          loop(tail).map(UnitaryNode(head, _))
        case _ => None
      }

    def loop(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case Nil =>
          None
        case head :: Nil =>
          constant(head) orElse
            variable(head)
        case _ =>
          binaryPrefixOperation(input) orElse
            binaryInfixOperation(input) orElse
            unitaryPrefixOperation(input) orElse
            parenthesis(input)
      }

    val tokenized = Tokeninizer[O, S, V](input, lang)(using literalParser)

    tokenized.toOption.flatMap { tokens =>
      if (checkParenthesis(tokens))
        loop(tokens)
      else
        None
    }
  }

  private def splitByRegardingParenthesis[O, S, V](
      input: List[Token[O, S, V]],
      splitter: Token[O, S, V]
  ): Option[(List[Token[O, S, V]], List[Token[O, S, V]])] = {
    var k = -1
    var c = 0
    var i = 0
    while (i < input.length) {
      input(i) match {
        case CloseParens => c -= 1
        case OpenParens  => c += 1
        case `splitter` if c == 0 =>
          k = i;
          i = input.length
        case _ =>
      }
      i += 1
    }
    if (k == -1) None
    else Some((input.take(k), input.drop(k + 1)))
  }

  private def checkParenthesis[O, S, V](tokenList: List[Token[O, S, V]]): Boolean = {
    @tailrec
    def loop(list: List[Token[O, S, V]], depth: Int): Boolean =
      list match {
        case OpenParens :: tail             => loop(tail, depth + 1)
        case CloseParens :: _ if depth == 0 => false
        case CloseParens :: tail            => loop(tail, depth - 1)
        case _ :: tail                      => loop(tail, depth)
        case Nil                            => depth == 0
      }

    loop(tokenList, 0)
  }
}
