package mathParser.double

import mathParser.slices.{AbstractSyntaxTree, Compile, FreeVariables}

trait DoubleCompile extends Compile {
  _: AbstractSyntaxTree with DoubleOperators with FreeVariables =>

  private def functionString(node: Node): String = node.fold[String](
    _.toString,
    {
      case (Neg, child) => s"-($child)"
      case (Sin, child) => s"Math.sin($child)"
      case (Cos, child) => s"Math.cos($child)"
      case (Tan, child) => s"Math.tan($child)"
      case (Asin, child) => s"Math.asin($child)"
      case (Acos, child) => s"Math.acos($child)"
      case (Atan, child) => s"Math.atan($child)"
      case (Sinh, child) => s"Math.sinh($child)"
      case (Cosh, child) => s"Math.cosh($child)"
      case (Tanh, child) => s"Math.tanh($child)"
      case (Exp, child) => s"Math.exp($child)"
      case (Log, child) => s"Math.log($child)"
    }, {
      case (Plus, left, right) => s"(($left) + ($right))"
      case (Minus, left, right) => s"(($left) - ($right))"
      case (Times, left, right) => s"(($left) * ($right))"
      case (Divided, left, right) => s"(($left) / ($right))"
      case (Power, left, right) => s"Math.pow($left, $right)"
    },
    _.name)


  def compile1(node: Node): Option[Double => Double] = {
    preconditions1()
    compileAndCast[Double => Double](
      s"""
         |new Function1[Double, Double]{
         |  def apply(${freeVariables(0).name}:Double):Double = ${functionString(node)}
         |}
         |""".stripMargin)
      .toOption
  }

  def compile2(node: Node): Option[(Double, Double) => Double] = {
    preconditions2()
    compileAndCast[(Double, Double) => Double](
      s"""
         |new Function2[Double, Double, Double]{
         |  def apply(${freeVariables(0).name}:Double, ${freeVariables(1).name}:Double):Double = ${functionString(node)}
         |}
         |""".stripMargin)
      .toOption
  }
}
