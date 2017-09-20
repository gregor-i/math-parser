package mathParser

import spire.math.Complex

package object complex {
  type C = Complex[Double]
  type Lang = ComplexLanguage.type
  val Lang = ComplexLanguage
  val Derive = ComplexDerive
  val Compile = ComplexCompile
}
