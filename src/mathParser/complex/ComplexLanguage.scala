package mathParser.complex

import mathParser.algebra.SpireLanguage
import spire.implicits._
import spire.math.Complex

class ComplexLanguage(freeVariables: Seq[Symbol])
  extends SpireLanguage[Complex[Double]](freeVariables)
    with ComplexCompile
