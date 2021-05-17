package mathParser.algebra.compile

import mathParser.Compiler
import mathParser.algebra._
import spire.math.Complex

import scala.util.Try

object SpireCompiler {

  private def compileAndCast[A](scalaCode: String): Try[A] =
    Try {
      import reflect.runtime.currentMirror
      import tools.reflect.ToolBox
      currentMirror.mkToolBox().compile(currentMirror.mkToolBox().parse(scalaCode))().asInstanceOf[A]
    }

  private def scalaCode[A, V](node: SpireNode[A, V])(encodeLiteral: A => String)(variableNames: V => String): String =
    node.fold[String](
      encodeLiteral, {
        case (Neg, child)  => s"-($child)"
        case (Sin, child)  => s"trig.sin($child)"
        case (Cos, child)  => s"trig.cos($child)"
        case (Tan, child)  => s"trig.tan($child)"
        case (Asin, child) => s"trig.asin($child)"
        case (Acos, child) => s"trig.acos($child)"
        case (Atan, child) => s"trig.atan($child)"
        case (Sinh, child) => s"trig.sinh($child)"
        case (Cosh, child) => s"trig.cosh($child)"
        case (Tanh, child) => s"trig.tanh($child)"
        case (Exp, child)  => s"trig.exp($child)"
        case (Log, child)  => s"trig.log($child)"
      }, {
        case (Plus, left, right)    => s"field.plus($left, $right)"
        case (Minus, left, right)   => s"field.minus($left, $right)"
        case (Times, left, right)   => s"field.times($left, $right)"
        case (Divided, left, right) => s"field.div($left, $right)"
        case (Power, left, right)   => s"nroot.fpow($left, $right)"
      },
      variableNames
    )

  implicit def compilerDouble1[V <: Singleton]: Compiler[SpireUnitaryOperator, SpireBinaryOperator, Double, V, Double => Double] =
    node =>
      compileAndCast[Double => Double](
        s"""import spire.implicits._
           |
           |new Function1[Double, Double]{
           |  val field: spire.algebra.Field[Double] = spire.algebra.Field[Double]
           |  val nroot: spire.algebra.NRoot[Double] = spire.algebra.NRoot[Double]
           |  val trig: spire.algebra.Trig[Double] = spire.algebra.Trig[Double]
           |
           |  def apply(v1 : Double): Double =
           |    ${scalaCode(node)(_.toString)(_ => "v1")}
           |}
           |""".stripMargin
      )

  implicit def compilerComplex1[V <: Singleton]
      : Compiler[SpireUnitaryOperator, SpireBinaryOperator, Complex[Double], V, Complex[Double] => Complex[Double]] =
    node =>
      compileAndCast[Complex[Double] => Complex[Double]](
        s"""import spire.implicits._
           |import spire.math.Complex
           |
           |new Function1[Complex[Double], Complex[Double]]{
           |  val field: spire.algebra.Field[Complex[Double]] = spire.algebra.Field[Complex[Double]]
           |  val nroot: spire.algebra.NRoot[Complex[Double]] = spire.algebra.NRoot[Complex[Double]]
           |  val trig: spire.algebra.Trig[Complex[Double]] = spire.algebra.Trig[Complex[Double]]
           |
           |  def apply(v1 : Complex[Double]): Complex[Double] =
           |    ${scalaCode(node)(c => s"Complex[Double](${c.real}, ${c.imag})")(_ => "v1")}
           |}
           |""".stripMargin
      )
}
