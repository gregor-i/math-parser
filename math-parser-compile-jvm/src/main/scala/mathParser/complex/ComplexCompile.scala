package mathParser.complex

import mathParser.CompileAndCast
import mathParser.algebra.SpireOperators
import mathParser.slices.{AbstractSyntaxTree, Compile, FreeVariables}
import spire.math.Complex

object ComplexCompile extends Compile[ComplexLanguage] {

  private def scalaCode(language: ComplexLanguage)(node: language.Node): String = node.fold[String](
    v => s"Complex[Double](${v.real}, ${v.imag})"
    , {
      case (language.Neg, child) => s"-($child)"
      case (language.Sin, child) => s"($child).sin"
      case (language.Cos, child) => s"($child).cos"
      case (language.Tan, child) => s"($child).tan"
      case (language.Asin, child) => s"($child).asin"
      case (language.Acos, child) => s"($child).acos"
      case (language.Atan, child) => s"($child).atan"
      case (language.Sinh, child) => s"($child).sinh"
      case (language.Cosh, child) => s"($child).cosh"
      case (language.Tanh, child) => s"($child).tanh"
      case (language.Exp, child) => s"($child).exp"
      case (language.Log, child) => s"($child).log"
    }, {
      case (language.Plus, left, right) => s"($left + $right)"
      case (language.Minus, left, right) => s"($left - $right)"
      case (language.Times, left, right) => s"($left * $right)"
      case (language.Divided, left, right) => s"($left / $right)"
      case (language.Power, left, right) => s"($left ** $right)"
    },
    _.name)

  override def compile1(language: ComplexLanguage)(term: language.Node): Option[C => C] = {
    preconditions1(language)
    CompileAndCast[C => C](
      s"""import spire.implicits._
         |import spire.math.Complex
         |
         |new Function1[Complex[Double], Complex[Double]]{
         |  def apply(${language.freeVariables(0).name}:Complex[Double]): Complex[Double] =
         |    ${scalaCode(language)(term)}
         |}
         |
      """.stripMargin
    ).toOption
  }

  override def compile2(language: ComplexLanguage)(term: language.Node): Option[(C, C) => C] = {
    preconditions2(language)
    CompileAndCast[(C, C) => C](
      s"""import spire.implicits._
         |import spire.math.Complex
         |
         |new Function2[Complex[Double], Complex[Double], Complex[Double]]{
         |  def apply(${language.freeVariables(0).name}:Complex[Double], ${language.freeVariables(1).name}:Complex[Double]): Complex[Double] =
         |    ${scalaCode(language)(term)}
         |}
         |
      """.stripMargin
    ).toOption
  }
}
