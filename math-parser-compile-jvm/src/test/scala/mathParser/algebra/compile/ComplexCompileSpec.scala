package mathParser.algebra.compile

import mathParser.algebra.compile.SpireCompiler._
import mathParser.SomeFunctions.someFunctions
import mathParser.implicits._
import mathParser.{MathParser, TestUtils}
import org.scalacheck.Prop._
import org.scalacheck.Shrink
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import spire.math.Complex

class ComplexCompileSpec extends FunSuite with Checkers with TestUtils {
  object X

  implicit def noShrink[T]: Shrink[T] = Shrink(_ => Stream.empty)

  test("'Compile to Native' for the complex language with parameter 'x'") {
    val lang = MathParser.complexLanguage
      .withVariables(List('x -> X))

    check(forAll(someFunctions) {
      term =>
        val ast = lang.parse(term).get
        val compiled = lang.compile[Complex[Double] => Complex[Double]](ast).get
        forAll(genComplex) {
          x => compiled(x) ==== lang.evaluate(ast)({ case X => x })
        }
    })
  }

  test("'Compile to Native' for the double language with parameter 'x'") {
    val lang = MathParser.doubleLanguage
      .withVariables(List('x -> X))

    check(forAll(someFunctions) {
      term =>
        val ast = lang.parse(term).get
        val compiled = lang.compile[Double => Double](ast).get
        forAll {
          (x: Double) => compiled(x) ==== lang.evaluate(ast)({ case X => x })
        }
    })
  }
}
