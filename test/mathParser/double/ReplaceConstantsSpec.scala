package mathParser.double

import org.specs2.mutable.Specification

class ReplaceConstantsSpec extends Specification {
  val lang = mathParser.MathParser.doubleLanguage('x)

  import lang._

  "replace constants" >> {
    replaceConstants(parse("sin(0)+x").get) == parse("0+x").get
  }
}
