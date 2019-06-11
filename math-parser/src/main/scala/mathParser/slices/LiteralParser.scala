package mathParser.slices

trait LiteralParser[+S] {
  def tryToParse(s: String): Option[S]
}

object NoLiterals extends LiteralParser[Nothing] {
  def tryToParse(s: String): None.type = None
}