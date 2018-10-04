package mathParser.complex

import mathParser.algebra.SpireOperators
import mathParser.slices.{AbstractSyntaxTree, Compile, FreeVariables}
import spire.math.Complex

trait ComplexCompile extends Compile {
  _ : AbstractSyntaxTree with SpireOperators[Complex[Double]] with FreeVariables =>

  private def scalaCode(node: Node): String = node match {
    case ConstantNode(v) => s"Complex[Double](${v.real}, ${v.imag})"
    case UnitaryNode(Neg, child) => s"-(${scalaCode(child)})"
    case UnitaryNode(Sin, child) => s"(${scalaCode(child)}).sin"
    case UnitaryNode(Cos, child) => s"(${scalaCode(child)}).cos"
    case UnitaryNode(Tan, child) => s"(${scalaCode(child)}).tan"
    case UnitaryNode(Asin, child) => s"(${scalaCode(child)}).asin"
    case UnitaryNode(Acos, child) => s"(${scalaCode(child)}).acos"
    case UnitaryNode(Atan, child) => s"(${scalaCode(child)}).atan"
    case UnitaryNode(Sinh, child) => s"(${scalaCode(child)}).sinh"
    case UnitaryNode(Cosh, child) => s"(${scalaCode(child)}).cosh"
    case UnitaryNode(Tanh, child) => s"(${scalaCode(child)}).tanh"
    case UnitaryNode(Exp, child) => s"(${scalaCode(child)}).exp"
    case UnitaryNode(Log, child) => s"(${scalaCode(child)}).log"

    case BinaryNode(Plus, left, right) => s"((${scalaCode(left)}) + (${scalaCode(right)}))"
    case BinaryNode(Minus, left, right) => s"((${scalaCode(left)}) - (${scalaCode(right)}))"
    case BinaryNode(Times, left, right) => s"((${scalaCode(left)}) * (${scalaCode(right)}))"
    case BinaryNode(Divided, left, right) => s"((${scalaCode(left)}) / (${scalaCode(right)}))"
    case BinaryNode(Power, left, right) => s"((${scalaCode(left)}) ** (${scalaCode(right)}))"

    case Variable(name) => name.name
  }

  override def compile1(term: Node): Option[C => C] = {
    preconditions1()
    compileAndCast[C => C](
      s"""import spire.implicits._
         |import spire.math.Complex
         |
         |new Function1[Complex[Double], Complex[Double]]{
         |  def apply(${freeVariables(0).name}:Complex[Double]): Complex[Double] = ${scalaCode(term)}
         |}
         |
      """.stripMargin
    ).toOption
  }

  override def compile2(term: Node): Option[(C, C) => C] = {
    preconditions2()
    compileAndCast[(C, C) => C](
      s"""import spire.implicits._
         |import spire.math.Complex
         |
         |new Function2[Complex[Double], Complex[Double], Complex[Double]]{
         |  def apply(${freeVariables(0).name}:Complex[Double], ${freeVariables(1).name}:Complex[Double]): Complex[Double] = ${scalaCode(term)}
         |}
         |
      """.stripMargin
    ).toOption
  }
}
