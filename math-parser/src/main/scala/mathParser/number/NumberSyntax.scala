package mathParser.number

import mathParser.AbstractSyntaxTree
import mathParser.AbstractSyntaxTree.*
import mathParser.number.ComplexLanguage
import mathParser.number.NumberOperator.*
import mathParser.number.NumberBinaryOperator
import mathParser.number.NumberUnitaryOperator

object NumberSyntax {
  def neg[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]  = UnitaryNode(Neg, t)
  def sin[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]  = UnitaryNode(Sin, t)
  def cos[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]  = UnitaryNode(Cos, t)
  def tan[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]  = UnitaryNode(Tan, t)
  def asin[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = UnitaryNode(Asin, t)
  def acos[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = UnitaryNode(Acos, t)
  def atan[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = UnitaryNode(Atan, t)
  def sinh[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = UnitaryNode(Sinh, t)
  def cosh[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = UnitaryNode(Cosh, t)
  def tanh[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = UnitaryNode(Tanh, t)
  def exp[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]  = UnitaryNode(Exp, t)
  def log[S, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]  = UnitaryNode(Log, t)

  def sqrt[S: Number, V](t: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = BinaryNode(Power, t, ConstantNode(Number.oneHalf[S]))

  extension [S, V](t1: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]) {
    def +(t2: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = BinaryNode(Plus, t1, t2)
    def -(t2: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = BinaryNode(Minus, t1, t2)
    def *(t2: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = BinaryNode(Times, t1, t2)
    def /(t2: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = BinaryNode(Divided, t1, t2)
    def ^(t2: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = BinaryNode(Power, t1, t2)
  }
}
