package mathParser.complex

import mathParser.SomeFunctions.someFunctions
import mathParser.{MathParser, TestUtils}
import org.scalacheck.Prop._
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

class ComplexCompileSpec extends FunSuite with Checkers with TestUtils {
  val lang = MathParser.complexLanguage('x)

  test("'Compile to Native' for the complex language with parameter 'x'") {
    check(forAll(someFunctions) {
      term =>
        val ast = lang.parse(term).get
        val compiled = ComplexCompile.compile1(lang)(ast).get
        forAll(genComplex) {
          x => compiled(x) ==== lang.evaluate(ast)({ case 'x => x })
        }
    })
  }
}
