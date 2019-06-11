package mathParser.complex

import mathParser.algebra.SpireLanguage
import spire.implicits._
import spire.math.Complex

class ComplexLanguage(freeVariables: Seq[Symbol])
  extends SpireLanguage[Complex[Double]](freeVariables){

  case object i extends SpireConstant('i, Complex(0d, 1d))

  override def constants(): Seq[SpireConstant] = Seq(e, pi, i)
}
