package mathParser.complex

import mathParser.complex.ComplexLanguage.*
import mathParser.complex.ComplexBinaryOperator.*
import mathParser.complex.ComplexUnitaryOperator.*
import mathParser.*

object Syntax {
  def neg[V](t: ComplexNode[V]): ComplexNode[V]  = UnitaryNode(Neg, t)
  def sin[V](t: ComplexNode[V]): ComplexNode[V]  = UnitaryNode(Sin, t)
  def cos[V](t: ComplexNode[V]): ComplexNode[V]  = UnitaryNode(Cos, t)
  def tan[V](t: ComplexNode[V]): ComplexNode[V]  = UnitaryNode(Tan, t)
  def asin[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Asin, t)
  def acos[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Acos, t)
  def atan[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Atan, t)
  def sinh[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Sinh, t)
  def cosh[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Cosh, t)
  def tanh[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Tanh, t)
  def exp[V](t: ComplexNode[V]): ComplexNode[V]  = UnitaryNode(Exp, t)
  def log[V](t: ComplexNode[V]): ComplexNode[V]  = UnitaryNode(Log, t)

  def sqrt[V](t: ComplexNode[V]): ComplexNode[V] = BinaryNode(Power, t, ConstantNode(Complex(0.5, 0.0)))

  extension [V](t1: ComplexNode[V]) {
    def +(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Plus, t1, t2)
    def -(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Minus, t1, t2)
    def *(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Times, t1, t2)
    def /(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Divided, t1, t2)
    def ^(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Power, t1, t2)
  }

  def zero[V]: ComplexNode[V] = ConstantNode(Complex(0.0, 0.0))
  def one[V]: ComplexNode[V]  = ConstantNode(Complex(1.0, 0.0))
  def two[V]: ComplexNode[V]  = ConstantNode(Complex(2.0, 0.0))
}
