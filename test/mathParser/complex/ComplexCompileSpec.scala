package mathParser.complex

import mathParser.SomeFunctions.someFunctions
import mathParser.{MathParser, TestUtils}
import org.scalacheck.Prop._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

class ComplexCompileSpec extends Specification with ScalaCheck with TestUtils {
  val lang = MathParser.complexLanguage('x)

  "'Compile to Native' for the complex language with parameter 'x'" >> {
    forAll(someFunctions) {
      term =>
        val ast = lang.parse(term).get
        val compiled = lang.compile1(ast).get
        forAll(genComplex) {
          x => compiled(x) ==== lang.evaluate(ast)({ case 'x => x })
        }
    }
  }
}
