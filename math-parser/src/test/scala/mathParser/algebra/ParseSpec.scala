package mathParser.algebra

import mathParser.algebra.SpireLanguage.syntax._
import mathParser.{ConstantNode, LiteralParser, MathParser}
import org.scalatest.{FunSuite, Matchers}
import spire.algebra.{Field, NRoot, Trig}
import mathParser.implicits._

class ParseSpec extends FunSuite with Matchers {

  sealed trait V
  case object A extends V
  case object B extends V
  case object C extends V
  case object X extends V

  def vList = List(
    "a" -> A,
    "b" -> B,
    "c" -> C,
    "x" -> X)

  testTemplate(MathParser.doubleLanguage.withVariables[V](vList), "double language")
  testTemplate(MathParser.realLanguage.withVariables[V](vList), "real language")
  testTemplate(MathParser.complexLanguage.withVariables[V](vList), "complex language")

  def testTemplate[A: Field: Trig: NRoot: LiteralParser](lang: SpireLanguage[A, V], langName: String) = {
    test(s"$langName: parsing constants, literals and variables") {
      for((name, value) <- lang.constants)
        lang.parse(name) shouldEqual Some(lang.constantNode(value))
      lang.parse("x") shouldEqual Some(lang.variable(X))
      lang.parse("undeclared") shouldEqual None
      lang.parse("2.5") shouldEqual Some(ConstantNode("2.5".toDouble))
      lang.parse("2..1") shouldEqual None
    }

    test(s"$langName: parsing parenthesis") {
      lang.parse("(x)") shouldEqual Some(lang.variable(X))
      lang.parse("((x))") shouldEqual Some(lang.variable(X))
    }

    test(s"$langName: parsing binary infix operation") {
      lang.parse("a+b") shouldEqual Some(lang.variable(A) + lang.variable(B))
      lang.parse("a*b") shouldEqual Some(lang.variable(A) * lang.variable(B))
      lang.parse("a^b") shouldEqual Some(lang.variable(A) ^ lang.variable(B))
    }

    test(s"$langName: parsing binary infix operators and following the operator priority") {
      lang.parse("a+b*c") shouldEqual Some(lang.variable(A) + (lang.variable(B) * lang.variable(C)))
      lang.parse("a*b+c") shouldEqual Some((lang.variable(A) * lang.variable(B)) + lang.variable(C))
    }

    test(s"$langName: parsing unitary prefix operators") {
      lang.parse("sin(x)") shouldEqual Some(sin(lang.variable(X)))
      lang.parse("sin()") shouldEqual None
      lang.parse("sin(x") shouldEqual None
      lang.parse("sin(x)+x") shouldEqual Some(sin(lang.variable(X)) + lang.variable(X))
      lang.parse("sin(x)+") shouldEqual None
      lang.parse("sin (x)") shouldEqual Some(sin(lang.variable(X)))
      lang.parse("sin (x)+x") shouldEqual Some(sin(lang.variable(X)) + lang.variable(X))
    }

    test(s"$langName: parse prefix nagative operator") {
      lang.parse("-x") shouldEqual Some(neg(lang.variable(X)))
      lang.parse("sinx") shouldEqual None
      lang.parse("sinh") shouldEqual None
      lang.parse("sinh(x)") shouldEqual Some(sinh(lang.variable(X)))
      lang.parse("sinh x") shouldEqual Some(sinh(lang.variable(X)))
      lang.parse("sinh x + a") shouldEqual Some(sinh(lang.variable(X)) + lang.variable(A))
    }

    test(s"$langName: parse complex situations correctly") {
      lang.parse("(x-1)^4*x^4") shouldEqual lang.parse("((x-1)^4) * (x^4)")
      lang.parse("x^-x") shouldEqual lang.parse("x^(-x)")
      lang.parse("sin(x)*sin(5*x)") shouldEqual lang.parse("(sin(x))*(sin(5*x))")
    }
  }
}
