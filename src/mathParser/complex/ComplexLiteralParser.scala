package mathParser.complex

import mathParser.slices.LiteralParser
import spire.math.Complex

import scala.util.Try

object ComplexLiteralParser extends LiteralParser[C] {
  def tryToParse(s: String): Option[C] = Try(s.toDouble).toOption.map(Complex(_, 0))
}