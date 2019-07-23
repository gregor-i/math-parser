package mathParser.algebra.compile

import mathParser.SomeFunctions.someFunctions
import mathParser.algebra.{SpireBinaryOperator, SpireLanguage, SpireUnitaryOperator}
import mathParser.implicits._
import mathParser.{Compiler, LiteralParser, MathParser}
import org.scalatest.{Assertion, FunSuite, Matchers}
import spire.algebra.{Field, NRoot, Trig}
import mathParser.algebra.compile.SpireCompiler.compilerDouble1
import mathParser.algebra.compile.SpireCompiler.compilerComplex1

import scala.util.Try

class CompileSpec extends FunSuite with Matchers {
  case object X
  type V = X.type

  testTemplate(MathParser.doubleLanguage, "double language")
  testTemplate(MathParser.complexLanguage, "complex language")

  def functionEquality[A: Field](f1: A => A, f2: A => A): Assertion = {
    for (x <- Seq(1.0, 2.0, 0.0, Math.PI, Math.E, -1.0, -2.0, 1000.0, -1000.0).map(Field[A].fromDouble)) {
      Try(f1(x)) equals Try(f2(x))
    }
    succeed
  }

  def testTemplate[A: Field : Trig : NRoot : LiteralParser](_lang: SpireLanguage[A, Nothing], langName: String)
                                                           (implicit compile: Compiler[SpireUnitaryOperator, SpireBinaryOperator, A, V, A => A]) = {
    val lang = _lang.withVariables(List("x" -> X))

    for (term <- someFunctions)
      test(s"$langName: compile $term") {
        val ast = lang.parse(term).get
        val compiled: A => A = lang.compile[A => A](ast).get
        functionEquality[A](compiled, x => lang.evaluate(ast)({ case X => x }))
      }
  }
}
