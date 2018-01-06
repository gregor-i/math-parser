package mathParser.double

import mathParser.slices.{AbstractSyntaxTree, Compile, FreeVariables}

trait DoubleCompile extends Compile {
  _: AbstractSyntaxTree with DoubleOperators with FreeVariables =>

  private def functionString(node: Node): String = node match {
    case ConstantNode(v) => v.toString
    case UnitaryNode(Neg, child) => s"-(${functionString(child)})"
    case UnitaryNode(Sin, child) => s"Math.sin(${functionString(child)})"
    case UnitaryNode(Cos, child) => s"Math.cos(${functionString(child)})"
    case UnitaryNode(Tan, child) => s"Math.tan(${functionString(child)})"
    case UnitaryNode(Asin, child) => s"Math.asin(${functionString(child)})"
    case UnitaryNode(Acos, child) => s"Math.acos(${functionString(child)})"
    case UnitaryNode(Atan, child) => s"Math.atan(${functionString(child)})"
    case UnitaryNode(Sinh, child) => s"Math.sinh(${functionString(child)})"
    case UnitaryNode(Cosh, child) => s"Math.cosh(${functionString(child)})"
    case UnitaryNode(Tanh, child) => s"Math.tanh(${functionString(child)})"
    case UnitaryNode(Exp, child) => s"Math.exp(${functionString(child)})"
    case UnitaryNode(Log, child) => s"Math.log(${functionString(child)})"

    case BinaryNode(Plus, left, right) => s"((${functionString(left)}) + (${functionString(right)}))"
    case BinaryNode(Minus, left, right) => s"((${functionString(left)}) - (${functionString(right)}))"
    case BinaryNode(Times, left, right) => s"((${functionString(left)}) * (${functionString(right)}))"
    case BinaryNode(Divided, left, right) => s"((${functionString(left)}) / (${functionString(right)}))"
    case BinaryNode(Power, left, right) => s"Math.pow(${functionString(left)}, ${functionString(right)})"

    case Variable(name) => name.name
  }


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
