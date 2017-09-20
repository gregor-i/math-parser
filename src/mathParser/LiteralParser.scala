package mathParser

trait LiteralParser[S] {
  def tryToParse(s:String): Option[S]
}
