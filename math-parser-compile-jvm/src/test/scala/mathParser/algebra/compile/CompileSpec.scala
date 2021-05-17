package mathParser.algebra.compile

import mathParser.SomeFunctions.someFunctions
import mathParser.SpireImplicits._
import mathParser.algebra.compile.SpireCompiler.{compilerComplex1, compilerDouble1}
import mathParser.algebra.{SpireBinaryOperator, SpireLanguage, SpireUnitaryOperator}
import mathParser.{Compiler, LiteralParser, SpireLanguages}
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import spire.algebra.{Field, NRoot, Trig}

import scala.util.Try

class CompileSpec extends AnyFunSuite with Matchers {
  case object X
  type V = X.type

  testTemplate(SpireLanguages.doubleLanguage, "double language")
  testTemplate(SpireLanguages.complexLanguage, "complex language")

  def functionEquality[A: Field](f1: A => A, f2: A => A): Assertion = {
    for (x <- Seq(1.0, 2.0, 0.0, Math.PI, Math.E, -1.0, -2.0, 1000.0, -1000.0).map(Field[A].fromDouble)) {
      Try(f1(x)) equals Try(f2(x))
    }
    succeed
  }

  def testTemplate[A: Field: Trig: NRoot: LiteralParser](_lang: SpireLanguage[A, Nothing], langName: String)(
      implicit compile: Compiler[SpireUnitaryOperator, SpireBinaryOperator, A, V, A => A]
  ) = {
    val lang = _lang.withVariables(List("x" -> X))

    for (term <- someFunctions)
      test(s"$langName: compile $term") {
        val ast              = lang.parse(term).get
        val compiled: A => A = lang.compile[A => A](ast).get
        functionEquality[A](compiled, x => lang.evaluate(ast)({ case X => x }))
      }
  }
}
