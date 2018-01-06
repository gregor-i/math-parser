package mathParser.complex

import mathParser.SomeFunctions.complexTrees
import mathParser.{Evaluate, TestUtils}
import org.scalacheck.Prop._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

class ComplexCompileSpec extends Specification with ScalaCheck with TestUtils {
  "'Compile to Native' for the complex language with parameter 'x'" >> {
    forAll(complexTrees) {
      term =>
        val f = ComplexCompile('x)(term).get
        forAll(genComplex) {
          x => f(x) ==== Evaluate(term)({ case 'x => x })
        }
    }
  }
}
