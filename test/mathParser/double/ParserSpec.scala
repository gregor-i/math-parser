package mathParser.double

import mathParser.MathParser
import org.specs2.mutable.Specification

class ParserSpec extends Specification {
  val lang = MathParser.doubleLanguage('x, 'h)

  val x = lang.Variable('x)
  val h = lang.Variable('h)

  import lang._

  "parsing constants, literals and variables" >> {
    parse("e") === Some(ConstantNode(Math.E))
    parse("pi") === Some(ConstantNode(Math.PI))
    parse("x") === Some(x)
    parse("undeclared") === None
    parse("2.5") === Some(ConstantNode("2.5".toDouble))
    parse("2..1") === None
  }

  "parsing parenthesis" >> {
    parse("(x)") === Some(x)
    parse("((x))") === Some(x)
  }

  "parsing binary infix operation" >> {
    parse("2+3") === Some(ConstantNode(2) + ConstantNode(3))
    parse("2*3") === Some(ConstantNode(2) * ConstantNode(3))
    parse("2^3") === Some(ConstantNode(2) ^ ConstantNode(3))
  }

  "parsing binary infix operators and following the operator priority" >> {
    parse("1+2*3") === Some(ConstantNode(1) + (ConstantNode(2) * ConstantNode(3)))
    parse("1*2+3") === Some((ConstantNode(1) * ConstantNode(2)) + ConstantNode(3))
  }

  "parsing unitary prefix operators" >> {
    parse("sin(x)") === Some(sin(x))
    parse("sin()") === None
    parse("sin(x") === None
    parse("sin(x)+x") === Some(sin(x) + x)
    parse("sin(x)+") === None
    parse("sin (x)") === Some(sin(x))
    parse("sin (x)+x") === Some(sin(x) + x)
  }

  "parse prefix nagative operator" >> {
    parse("-x") === Some(neg(x))
    parse("sinx") === None
    parse("sinh") === None
    parse("sinh(x)") === Some(sinh(x))
    parse("sinh x") === Some(sinh(x))
    parse("sinh x + 2") === Some(sinh(x) + ConstantNode(2))
  }

  "parse complex situations correctly" >> {
    parse("(x-1)^4*x^4") === parse("((x-1)^4) * (x^4)")
    parse("x^-x") === parse("x^(-x)")
    parse("sin(x)*sin(5*x)") === parse("(sin(x))*(sin(5*x))")
  }
}
