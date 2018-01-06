package mathParser
package double

import mathParser.SomeFunctions.someFunctions
import org.scalacheck.Prop._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification


class DoubleCompileSpec  extends Specification with ScalaCheck with TestUtils {
  val lang = MathParser.doubleLanguage('x)

  "'Compile to Native' for the double language with parameter 'x'" >> {
    forAll(someFunctions) {
      term =>
        val ast = lang.parse(term).get
        val compiled = lang.compile1(ast).get
        forAll {
          x: Double => compiled(x) ==== lang.evaluate(ast)({ case 'x => x })
        }
    }
  }
}
