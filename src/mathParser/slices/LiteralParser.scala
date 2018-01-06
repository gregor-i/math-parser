package mathParser.slices

trait LiteralParser[S] {
  def tryToParse(s: String): Option[S]
}

class NoLiterals[S] extends LiteralParser[S]{
  def tryToParse(s: String): Option[S] = None
}