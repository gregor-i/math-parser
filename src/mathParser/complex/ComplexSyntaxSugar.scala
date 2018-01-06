package mathParser.complex

import mathParser.slices.AbstractSyntaxTree

trait ComplexSyntaxSugar {
  _: AbstractSyntaxTree with ComplexOperators =>

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

  def constant(v:C) = ConstantNode(v)
}