
package mathParser.complex

import mathParser.{Language, LiteralParser}
import spire.math.Complex

import scala.util.Try

object ComplexLanguage extends Language[C]{
  override type Constant = ComplexConstant
  override type UnitaryOperator = ComplexUnitaryOperator
  override type BinaryOperator = ComplexBinaryOperator

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log)

  override def binaryOperators: Seq[BinaryOperator] = Seq.empty

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(Plus, Minus, Times, Divided, Power)

  override def constants(): Seq[Constant] = Seq(e, pi, i)

  val literalParser = new LiteralParser[C]{
    def tryToParse(s:String): Option[C] = Try(s.toDouble).toOption.map(Complex(_, 0))
  }
}

trait ComplexSyntaxSugar {
  import mathParser.AbstractSyntaxTree

  type Node = AbstractSyntaxTree.Node[C, Lang]
  type UnitaryNode = AbstractSyntaxTree.UnitaryNode[C, Lang]
  def UnitaryNode(op:ComplexLanguage.UnitaryOperator, t:Node) = AbstractSyntaxTree.UnitaryNode[C, Lang](op, t)
  type BinaryNode = AbstractSyntaxTree.BinaryNode[C, Lang]
  def BinaryNode(op:ComplexLanguage.BinaryOperator, t1:Node, t2:Node) = AbstractSyntaxTree.BinaryNode[C, Lang](op, t1, t2)
  type Constant = AbstractSyntaxTree.Constant[C, Lang]

  def neg(t:Node): Node = UnitaryNode(Neg, t)
  def sin(t:Node): Node = UnitaryNode(Sin, t)
  def cos(t:Node): Node = UnitaryNode(Cos, t)
  def tan(t:Node): Node = UnitaryNode(Tan, t)
  def asin(t:Node): Node = UnitaryNode(Asin, t)
  def acos(t:Node): Node = UnitaryNode(Acos, t)
  def atan(t:Node): Node = UnitaryNode(Atan, t)
  def sinh(t:Node): Node = UnitaryNode(Sinh, t)
  def cosh(t:Node): Node = UnitaryNode(Cosh, t)
  def tanh(t:Node): Node = UnitaryNode(Tanh, t)
  def exp(t:Node): Node = UnitaryNode(Exp, t)
  def log(t:Node): Node = UnitaryNode(Log, t)

  def plus(t1:Node, t2:Node) = BinaryNode(Plus, t1, t2)
  def minus(t1:Node, t2:Node) = BinaryNode(Minus, t1, t2)
  def times(t1:Node, t2:Node) = BinaryNode(Times, t1, t2)
  def divided(t1:Node, t2:Node) = BinaryNode(Divided, t1, t2)
  def power(t1:Node, t2:Node) = BinaryNode(Power, t1, t2)

  def constant(v:C) = AbstractSyntaxTree.Constant[C, Lang](v)
}
