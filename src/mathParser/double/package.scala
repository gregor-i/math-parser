package mathParser

package object double {
  val Lang = DoubleLanguage
  val Derive = DoubleDerive
  val Compile = DoubleCompile
  val Parser = new Parser[Double, DoubleLanguage.type](DoubleLanguage, DoubleLanguage.literalParser)
}
