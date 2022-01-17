package mathParser.number

import mathParser.number.DoubleLanguage
import mathParser.number.NumberOperator.Minus
import mathParser.number.NumberSyntax.*
import mathParser.{BuildIn, ConstantNode, UnitaryNode}
import org.scalatest.funsuite.AnyFunSuite
import mathParser.LiteralParser
import mathParser.Language
import mathParser.AbstractSyntaxTree

trait ParseProp { self: AnyFunSuite =>

  def testParsing[S](rawLang: Language[NumberOperator, S, Nothing])(using litParser: LiteralParser[S]) = {

    case object A
    case object B
    case object C
    case object X
    type V = A.type | B.type | C.type | X.type

    val lang = rawLang
      .addVariable("a", A)
      .addVariable("b", B)
      .addVariable("c", C)
      .addVariable("x", X)

    val one = litParser.parseAll("1").right.get

    test("parsing constants, literals and variables") {
      for ((name, value) <- lang.constants)
        assert(lang.parse(name) == Some(value.constant[V]))
      assert(lang.parse("x") == Some(X.variable[S]))
      assert(lang.parse("1").isDefined)
      assert(lang.parse("-1").contains(neg(ConstantNode(one))))
      assert(lang.parse("undeclared") == None)
      assert(lang.parse("2.5") == litParser.parseAll("2.5").toOption.map(ConstantNode.apply))
      assert(lang.parse("2..1") == litParser.parseAll("2..5").toOption.map(ConstantNode.apply))
    }

    test("parsing parenthesis") {
      assert(lang.parse("(x)") == Some(X.variable[S]))
      assert(lang.parse("((x))") == Some(X.variable[S]))
    }

    test("parsing binary infix operation") {
      assert(lang.parse("a+b") == Some(A.variable[S] + B.variable[S]))
      assert(lang.parse("a*b") == Some(A.variable[S] * B.variable[S]))
      assert(lang.parse("a^b") == Some(A.variable[S] ^ B.variable[S]))
    }

    test("parsing binary infix operators and following the operator priority") {
      assert(lang.parse("a+b*c") == Some(A.variable[S] + (B.variable[S] * C.variable[S])))
      assert(lang.parse("a*b+c") == Some((A.variable[S] * B.variable[S]) + C.variable[S]))
    }

    test("parsing unitary prefix operators") {
      assert(lang.parse("sin(x)") == Some(sin(X.variable[S])))
      assert(lang.parse("sin()") == None)
      assert(lang.parse("sin(x") == None)
      assert(lang.parse("sin(x)+x") == Some(sin(X.variable[S]) + X.variable[S]))
      assert(lang.parse("sin(x)+") == None)
      assert(lang.parse("sin (x)") == Some(sin(X.variable[S])))
      assert(lang.parse("sin (x)+x") == Some(sin(X.variable[S]) + X.variable[S]))
      for (op <- lang.unitaryOperators)
        assert(lang.parse(s"${op._1}(x)") == Some(UnitaryNode(op._2, X.variable[S])))
    }

    test("parse prefix operators") {
      assert(lang.parse("-x") == Some(neg(X.variable[S])))
      assert(lang.parse("sinx") == None)
      assert(lang.parse("sinh") == None)
      assert(lang.parse("sinh(x)") == Some(sinh(X.variable[S])))
      assert(lang.parse("sinh x") == Some(sinh(X.variable[S])))
      assert(lang.parse("sinh x + a") == Some(sinh(X.variable[S]) + A.variable[S]))
    }

    test("parse complex situations correctly") {
      assert(lang.parse("(x-1)^4*x^4").isDefined)
      assert(lang.parse("(x-1)^4*x^4") == lang.parse("((x-1)^4) * (x^4)"))
      assert(lang.parse("x^-x").isDefined)
      assert(lang.parse("x^-x") == lang.parse("x^(-x)"))
      assert(lang.parse("sin(x)*sin(5*x)").isDefined)
      assert(lang.parse("sin(x)*sin(5*x)") == lang.parse("(sin(x))*(sin(5*x))"))
    }

    test("parse the sample functions ") {
      for (function <- SomeFunctions.someFunctions)
        assert(lang.parse(function).isDefined, s"$function could not be parsed")
    }

    test("parse expressions with many parenthesis") {
      val lang = rawLang.addVariable("a", A).addVariable("x", X)

      assert(lang.parse(SomeFunctions.manyParenthesis).isDefined)
    }

    test("parse expressions with many operators") {
      val lang = rawLang.addVariable("a", A).addVariable("x", X)

      assert(lang.parse(SomeFunctions.manyOperators).isDefined)
    }
  }
}
