package mathParser.algebra

import mathParser.Implicits._
import mathParser.Syntax._
import mathParser.{Derive, Language, LiteralParser, MathParser}
import org.scalatest.funsuite.AnyFunSuite

class DeriveSpec extends AnyFunSuite {
  case object X
  type V = X.type

  testTemplate(MathParser.complexLanguage, "complex language")

  def testTemplate[UO, BO, S](
      _lang: Language[UO, BO, S, Nothing],
      langName: String
  )(implicit litParser: LiteralParser[S], derive: Derive[UO, BO, S, X.type]) = {
    val lang = _lang.withVariables[X.type](List("x" -> X))

    import lang.parse

    test(s"$langName: simple functions") {
      assert(parse("x*x").get.derive(X) === parse("1*x+1*x").get)
      assert(parse("x + x").get.derive(X) === parse("1 + 1").get)
      assert(parse("x*x + x").get.derive(X) === parse("(1*x+1*x) + 1").get)
    }
  }
}
