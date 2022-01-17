package mathParser.number

import org.scalatest.funsuite.AnyFunSuite
import mathParser.BuildIn
import mathParser.number.DoubleLanguage.given

class DoubleLangSpec extends AnyFunSuite with NumberProps {
  testNumberLanguage(BuildIn.doubleLanguage)
}
