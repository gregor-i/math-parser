package mathParser.double

import mathParser.CompileAndCast
import mathParser.algebra.SpireOperators
import mathParser.slices.{AbstractSyntaxTree, Compile, FreeVariables}

object DoubleCompile extends Compile[DoubleLanguage] {

  private def functionString(language: DoubleLanguage)(node: language.Node): String = node.fold[String](
    _.toString,
    {
      case (language.Neg, child) => s"-($child)"
      case (language.Sin, child) => s"Math.sin($child)"
      case (language.Cos, child) => s"Math.cos($child)"
      case (language.Tan, child) => s"Math.tan($child)"
      case (language.Asin, child) => s"Math.asin($child)"
      case (language.Acos, child) => s"Math.acos($child)"
      case (language.Atan, child) => s"Math.atan($child)"
      case (language.Sinh, child) => s"Math.sinh($child)"
      case (language.Cosh, child) => s"Math.cosh($child)"
      case (language.Tanh, child) => s"Math.tanh($child)"
      case (language.Exp, child) => s"Math.exp($child)"
      case (language.Log, child) => s"Math.log($child)"
    }, {
      case (language.Plus, left, right) => s"($left + $right)"
      case (language.Minus, left, right) => s"($left - $right)"
      case (language.Times, left, right) => s"($left * $right)"
      case (language.Divided, left, right) => s"($left / $right)"
      case (language.Power, left, right) => s"Math.pow($left, $right)"
    },
    _.name)


  def compile1(language: DoubleLanguage)(node: language.Node): Option[Double => Double] = {
    preconditions1(language)
    CompileAndCast[Double => Double](
      s"""
         |new Function1[Double, Double]{
         |  def apply(${language.freeVariables(0).name}:Double):Double =
         |     ${functionString(language)(node)}
         |}
         |""".stripMargin)
      .toOption
  }

  def compile2(language: DoubleLanguage)(node: language.Node): Option[(Double, Double) => Double] = {
    preconditions2(language)
    CompileAndCast[(Double, Double) => Double](
      s"""
         |new Function2[Double, Double, Double]{
         |  def apply(${language.freeVariables(0).name}:Double, ${language.freeVariables(1).name}:Double):Double =
         |    ${functionString(language)(node)}
         |}
         |""".stripMargin)
      .toOption
  }
}
