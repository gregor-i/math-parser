package mathParser
package double

import mathParser.SomeFunctions.someFunctions
import org.scalacheck.Prop._
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers


class DoubleCompileSpec extends FunSuite with Checkers with TestUtils {
  val lang = MathParser.doubleLanguage('x)

  test("'Compile to Native' for the double language with parameter 'x'") {
    check(forAll(someFunctions) {
      term =>
        val ast = lang.parse(term).get
        val compiled = lang.compile1(ast).get
        forAll {
          x: Double => compiled(x) ==== lang.evaluate(ast)({ case 'x => x })
        }
    })
  }
}
