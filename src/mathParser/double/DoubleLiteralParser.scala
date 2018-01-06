package mathParser.double

import mathParser.slices.LiteralParser

import scala.util.Try

object DoubleLiteralParser extends LiteralParser[Double] {
    def tryToParse(s:String): Option[Double] = Try(s.toDouble).toOption
}