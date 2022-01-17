package mathParser

import cats.parse.Parser
import scala.util.chaining.*

sealed trait Token[+O, +S, +V]
object Token {
  case object Comma            extends Token[Nothing, Nothing, Nothing]
  case object OpenParens       extends Token[Nothing, Nothing, Nothing]
  case object CloseParens      extends Token[Nothing, Nothing, Nothing]
  case class Scalar[S](s: S)   extends Token[Nothing, S, Nothing]
  case class Operator[O](o: O) extends Token[O, Nothing, Nothing]
  case class Variable[V](v: V) extends Token[Nothing, Nothing, V]
}

object Tokeninizer {
  import Token._

  private def parseParens[O, S, V]: Parser[Token[Nothing, Nothing, Nothing]] =
    Parser.char('(').as(OpenParens) |
      Parser.char(')').as(CloseParens) |
      Parser.char(',').as(Comma)

  private def isSymbolic(char: Char): Boolean = !char.isLetterOrDigit

  private def parseString(string: String): Parser[Unit] =
    if (isSymbolic(string.last))
      Parser.string(string)
    else
      Parser.string(string) <* (Parser.end | Parser.peek(Parser.anyChar.filter(isSymbolic)))

  private def parseOperators[O, S, V](lang: Language[O, S, V]): Parser[Operator[O]] =
    ((lang.binaryPrefixOperators ++ lang.binaryInfixOperators ++ lang.unitaryOperators): List[(String, O)])
      .map((name, op) => parseString(name).backtrack.as(Operator(op)))
      .pipe(Parser.oneOf)

  private def parseVars[O, S, V](lang: Language[O, S, V]): Parser[Variable[V]] =
    lang.variables
      .map((name, vari) => Parser.string(name).as(Variable(vari)))
      .pipe(Parser.oneOf)

  private def parseConst[O, S, V](lang: Language[O, S, V]): Parser[Scalar[S]] =
    lang.constants
      .map((name, value) => Parser.string(name).as(Scalar(value)))
      .pipe(Parser.oneOf)

  private def parseLiterals[S](literalParser: LiteralParser[S]): Parser[Scalar[S]] =
    literalParser.map(Scalar.apply)

  def apply[O, S, V](s: String, lang: Language[O, S, V])(using
      literalParser: LiteralParser[S]
  ): Either[Parser.Error, List[Token[O, S, V]]] = {
    val tokenParser =
      parseParens |
        parseOperators(lang) |
        parseConst(lang) |
        parseVars(lang) |
        parseLiterals(literalParser)

    val whitespace = Parser.char(' ').rep0

    tokenParser
      .repSep0(whitespace)
      .between(whitespace, whitespace)
      .parseAll(s)
      .map(_.toList)
  }
}
