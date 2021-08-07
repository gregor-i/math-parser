package mathParser.algebra

import mathParser.number.ComplexLanguage.given
import mathParser.Syntax._
import mathParser.{Derive, Language, LiteralParser, BuildIn}
import org.scalatest.funsuite.AnyFunSuite

class DeriveSpec extends AnyFunSuite {
  case object X
  type V = X.type

  testTemplate(BuildIn.complexLanguage, "complex language")

  def testTemplate[O, S](
      _lang: Language[O, S, Nothing],
      langName: String
  )(implicit litParser: LiteralParser[S], derive: Derive[O, S, X.type]) = {
    val lang = _lang.withVariables[X.type](List("x" -> X))

    import lang.parse

    test(s"$langName: simple functions") {
      assert(parse("x*x").get.derive(X) === parse("1*x+1*x").get)
      assert(parse("x + x").get.derive(X) === parse("1 + 1").get)
      assert(parse("x*x + x").get.derive(X) === parse("(1*x+1*x) + 1").get)
    }
  }
}
