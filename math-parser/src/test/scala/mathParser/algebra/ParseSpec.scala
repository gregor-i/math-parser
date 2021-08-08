package mathParser.algebra

import mathParser.number.DoubleLanguage.given
import mathParser.number.Complex
import mathParser.number.NumberSyntax._
import mathParser.{ConstantNode, BuildIn}
import org.scalatest.funsuite.AnyFunSuite

class ParseSpec extends AnyFunSuite {

  sealed trait V
  case object A extends V
  case object B extends V
  case object C extends V
  case object X extends V

  def vList = List("a" -> A, "b" -> B, "c" -> C, "x" -> X)

  val lang = BuildIn.doubleLanguage.withVariables[V](vList)

//  test("temp"){
//    import atto._
//    import Atto._
//    val parser = Atto.string("a") | Atto.string("b")
//
//    println(phrase(string("a") | string("b")).parse("b"))
//    println(phrase(Atto.choice(string("a"), string("b"))).parse("b"))
//    println(parser.parse("b"))
//    assert(parser.parse("b").option === Some("b"))
//  }

  test(s"complex: parsing constants, literals and variables") {
    for ((name, value) <- lang.constants)
      assert(lang.parse(name) === Some(lang.constantNode(value)))

    assert(lang.parse("x") === Some(lang.variable(X)))
    assert(lang.parse("undeclared") === None)
    assert(lang.parse("2..1") === None)
  }

  test(s"complex: parsing parenthesis") {
    assert(lang.parse("(x)") === Some(lang.variable(X)))
    assert(lang.parse("((x))") === Some(lang.variable(X)))
  }

  test(s"complex: parsing binary infix operation") {
    assert(lang.parse("a+b") === Some(lang.variable(A) + lang.variable(B)))
    assert(lang.parse("a*b") === Some(lang.variable(A) * lang.variable(B)))
    assert(lang.parse("a^b") === Some(lang.variable(A) ^ lang.variable(B)))
  }

  test(s"complex: parsing binary infix operators and following the operator priority") {
    assert(lang.parse("a+b*c") === Some(lang.variable(A) + (lang.variable(B) * lang.variable(C))))
    assert(lang.parse("a*b+c") === Some((lang.variable(A) * lang.variable(B)) + lang.variable(C)))
  }

  test(s"complex: parsing unitary prefix operators") {
    assert(lang.parse("sin(x)") === Some(sin(lang.variable(X))))
    assert(lang.parse("sin()") === None)
    assert(lang.parse("sin(x") === None)
    assert(lang.parse("sin(x)+x") === Some(sin(lang.variable(X)) + lang.variable(X)))
    assert(lang.parse("sin(x)+") === None)
    assert(lang.parse("sin (x)") === Some(sin(lang.variable(X))))
    assert(lang.parse("sin (x)+x") === Some(sin(lang.variable(X)) + lang.variable(X)))
  }

  test(s"complex: parse prefix nagative operator") {
    assert(lang.parse("-x") === Some(neg(lang.variable(X))))
    assert(lang.parse("sinx") === None)
    assert(lang.parse("sinh") === None)
    assert(lang.parse("sinh(x)") === Some(sinh(lang.variable(X))))
//    assert(lang.parse("sinh x") === Some(sinh(lang.variable(X))))
    assert(lang.parse("sinh(x) + a") === Some(sinh(lang.variable(X)) + lang.variable(A)))
  }

  test(s"complex: parse complex situations correctly") {
    assert(lang.parse("(x-1)^4*x^4") === lang.parse("((x-1)^4) * (x^4)"))
    assert(lang.parse("x^-x") === lang.parse("x^(-x)"))
    assert(lang.parse("sin(x)*sin(5*x)") === lang.parse("(sin(x))*(sin(5*x))"))
  }
}
