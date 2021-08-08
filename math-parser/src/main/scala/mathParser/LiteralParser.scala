package mathParser

type LiteralParser[S] = atto.Parser[S]

object LiteralParser:
  given noLiterals: LiteralParser[Nothing] =
    atto.Atto.err("no literals")
