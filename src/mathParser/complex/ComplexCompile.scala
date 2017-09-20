package mathParser.complex

import mathParser.AbstractSyntaxTree._
import mathParser.{Compile, Variable}

object ComplexCompile extends Compile[Lang] {
  def scalaCode(node: Node[Lang]): String = node match {
    case Constant(v) => s"Complex[Double](${v.real}, ${v.imag})"
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

  override def apply(v1: Variable)(term: Node[Lang]): Option[C => C] =
    compileAndCast[C => C](
      s"""import spire.implicits._
         |import spire.math.Complex
         |
         |new Function1[Complex[Double], Complex[Double]]{
         |  def apply(${v1.name}:Complex[Double]): Complex[Double] = ${scalaCode(term)}
         |}
         |
      """.stripMargin
    ).toOption

  override def apply(v1: Variable, v2: Variable)(term: Node[Lang]): Option[(C, C) => C] =
    compileAndCast[(C, C) => C](
      s"""import spire.implicits._
         |import spire.math.Complex
         |
         |new Function2[Complex[Double], Complex[Double], Complex[Double]]{
         |  def apply(${v1.name}:Complex[Double], ${v2.name}:Complex[Double]): Complex[Double] = ${scalaCode(term)}
         |}
         |
      """.stripMargin
    ).toOption
}
