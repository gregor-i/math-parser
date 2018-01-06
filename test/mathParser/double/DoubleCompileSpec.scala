package mathParser
package double

import mathParser.SomeFunctions.doubleTrees
import org.scalacheck.Prop._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification


class DoubleCompileSpec  extends Specification with ScalaCheck with TestUtils {
  "'Compile to Native' for the double language with parameter 'x'" >> {
    forAll(doubleTrees) {
      term =>
        val f = DoubleCompile('x)(term).get
        forAll {
          x: Double => f(x) ==== Evaluate(term)({ case 'x => x })
        }
    }
  }
}
