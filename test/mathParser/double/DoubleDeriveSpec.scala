package mathParser.double

import org.specs2.mutable.Specification

class DoubleDeriveSpec extends Specification {
  val lang = mathParser.MathParser.doubleLanguage('x)

  import lang._

  "derive simple functions" >> {
    derive(parse("x*x").get)('x) === parse("1*x+1*x").get
    derive(parse("x + x").get)('x) === parse("1 + 1").get
    derive(parse("x*x + x").get)('x) === parse("(1*x+1*x) + 1").get
  }
}
