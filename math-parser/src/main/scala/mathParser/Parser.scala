package mathParser

import scala.annotation.tailrec

object Parser {
  private type TokenList = List[String]

  def parse[O, S, V](lang: Language[O, S, V], literalParser: LiteralParser[S])(input: String): Option[AbstractSyntaxTree[O, S, V]] = {
    def binaryNode(
        input: TokenList,
        splitter: String,
        f: (AbstractSyntaxTree[O, S, V], AbstractSyntaxTree[O, S, V]) => AbstractSyntaxTree[O, S, V]
    ): Option[AbstractSyntaxTree[O, S, V]] =
      for {
        (sub1, sub2) <- splitByRegardingParenthesis(input, splitter)
        p1           <- loop(sub1)
        p2           <- loop(sub2)
      } yield f(p1, p2)

    def constant(input: String): Option[AbstractSyntaxTree[O, S, V]] =
      lang.constants
        .find(_._1 == input)
        .map(c => ConstantNode[O, S, V](c._2))

    def variable(input: String): Option[AbstractSyntaxTree[O, S, V]] =
      lang.variables
        .find(_._1 == input)
        .map(v => VariableNode(v._2))

    def literal(input: String): Option[AbstractSyntaxTree[O, S, V]] =
      literalParser
        .tryToParse(input)
        .map(literal => ConstantNode[O, S, V](literal))

    def parenthesis(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case "(" +: remaining :+ ")" => loop(remaining)
        case _                       => None
      }

    def binaryInfixOperation(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      lang.binaryInfixOperators
        .to(LazyList)
        .flatMap(op => binaryNode(input, op._1, BinaryNode[O, S, V](op._2, _, _)))
        .headOption

    def binaryPrefixOperation(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case head :: ("(" +: remaining :+ ")") =>
          for {
            operator <- lang.binaryPrefixOperators.find(_._1 == head)
            parsed   <- binaryNode(remaining, ",", BinaryNode[O, S, V](operator._2, _, _))
          } yield parsed
        case _ => None
      }

    def unitaryPrefixOperation(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      for {
        operator <- lang.unitaryOperators.find(_._1 == input.head)
        looped   <- loop(input.tail)
      } yield UnitaryNode[O, S, V](operator._2, looped)

    def loop(input: TokenList): Option[AbstractSyntaxTree[O, S, V]] =
      input match {
        case Nil =>
          None
        case head :: Nil =>
          constant(head) orElse
            variable(head) orElse
            literal(head)
        case _ =>
          binaryPrefixOperation(input) orElse
            binaryInfixOperation(input) orElse
            unitaryPrefixOperation(input) orElse
            parenthesis(input)
      }

    val tokenized = tokenize(input, lang)

    if (checkParenthesis(tokenized)) {
      loop(tokenized)
    } else {
      None
    }
  }

  private def splitByRegardingParenthesis(input: TokenList, splitter: String): Option[(TokenList, TokenList)] = {
    var k = -1
    var c = 0
    var i = 0
    while (i < input.length) {
      input(i) match {
        case ")" => c -= 1
        case "(" => c += 1
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

  private def tokenize(s: String, lang: Language[_, _, _]): TokenList = {
    val splitter = Set('(', ')', ',', ' ') ++ lang.binaryInfixOperators.collect {
      case (name, _) if name.length == 1 && !name.head.isLetterOrDigit => name.head
    }.toSet

    @tailrec
    def loop(s: List[Char], currentWord: List[Char], words: List[List[Char]]): List[List[Char]] =
      s match {
        case Nil                            => (currentWord.reverse :: words).reverse
        case head :: tail if splitter(head) => loop(tail, Nil, List(head) :: currentWord.reverse :: words)
        case head :: tail                   => loop(tail, head :: currentWord, words)
      }

    loop(s.toList, Nil, Nil)
      .map(_.mkString("").trim)
      .filter(_.nonEmpty)
  }

  private def checkParenthesis(tokenList: TokenList): Boolean = {
    @tailrec
    def loop(list: TokenList, depth: Int): Boolean =
      list match {
        case "(" :: tail            => loop(tail, depth + 1)
        case ")" :: _ if depth == 0 => false
        case ")" :: tail            => loop(tail, depth - 1)
        case _ :: tail              => loop(tail, depth)
        case Nil                    => depth == 0
      }

    loop(tokenList, 0)
  }
}
