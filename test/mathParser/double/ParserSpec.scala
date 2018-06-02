package mathParser.double

import mathParser.MathParser
import org.specs2.mutable.Specification

class ParserSpec extends Specification {
  val lang = MathParser.doubleLanguage('x)

  val x = lang.Variable('x)

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
    parse("sin (x)") === None
  }

}
