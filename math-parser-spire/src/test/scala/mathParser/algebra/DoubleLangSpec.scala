package mathParser.algebra

import mathParser.number.NumberSyntax.constant
import mathParser.number.{Complex, ComplexLanguage, NumberProps}
import mathParser.{BuildIn, SpireLanguages}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import spire.math.Complex
import spire.implicits.*
import mathParser.algebra.SpireLanguage.spireLiteralParser
import mathParser.algebra.SpireLanguage.given

class DoubleLangSpec extends AnyFunSuite with NumberProps {
  testNumerLanguage(SpireLanguages.doubleLanguage)
}
