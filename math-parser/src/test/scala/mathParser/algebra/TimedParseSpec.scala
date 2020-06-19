package mathParser.algebra

import mathParser.Implicits._
import mathParser.MathParser
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.time.{Millis, Span}


class TimedParseSpec extends AnyFunSuite with TimeLimitedTests {

  val timeLimit = Span(200, Millis)

  sealed trait V
  case object A extends V
  case object X extends V

  def vList = List(
    "a" -> A,
    "x" -> X)

  val lang = MathParser.complexLanguage.withVariables[V](vList)

    test(s"complex: parse expressions with many parenthesis") {
      val expr = "((((x * x + a) * (x * x + a) + a) * ((x * x + a) * (x * x + a) + a) + a) * (((x * x + a) * (x * x + a) + a) * ((x * x + a) * (x * x + a) + a) + a) + a)"
      assert(lang.parse(expr).isDefined)
    }

    test(s"complex: parse expressions with many operators"){
      val expr =  "35*x*x*x*x*x*x*x*x*x-180*x*x*x*x*x*x*x+378*x*x*x*x*x-420*x*x*x+315+x + a"
      assert(lang.parse(expr).isDefined)
    }
}
