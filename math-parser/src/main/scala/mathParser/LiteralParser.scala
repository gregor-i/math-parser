package mathParser

trait LiteralParser[S]:
  def tryToParse(s: String): Option[S]

object LiteralParser:
  given noLiterals: LiteralParser[Nothing] =
    new LiteralParser[Nothing]:
      def tryToParse(s: String): None.type = None
