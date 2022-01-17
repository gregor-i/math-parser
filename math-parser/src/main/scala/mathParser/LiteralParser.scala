package mathParser

type LiteralParser[S] = cats.parse.Parser[S]

//trait LiteralParser[S]:
//  def tryToParse(s: String): Option[S]

object LiteralParser:
  given noLiterals: LiteralParser[Nothing] = cats.parse.Parser.fail
//  given noLiterals: LiteralParser[Nothing] =
//    new LiteralParser[Nothing]:
//      def tryToParse(s: String): None.type = None
