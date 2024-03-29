package mathParser.number

import mathParser.{AbstractSyntaxTree => AST}
import mathParser.AbstractSyntaxTree.*
import mathParser.number.ComplexLanguage
import mathParser.number.NumberOperator.*
import mathParser.Operator

object NumberSyntax {
  def neg[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]          = UnitaryNode(Neg, t)
  def sin[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]          = UnitaryNode(Sin, t)
  def cos[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]          = UnitaryNode(Cos, t)
  def tan[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]          = UnitaryNode(Tan, t)
  def asin[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]         = UnitaryNode(Asin, t)
  def acos[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]         = UnitaryNode(Acos, t)
  def atan[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]         = UnitaryNode(Atan, t)
  def sinh[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]         = UnitaryNode(Sinh, t)
  def cosh[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]         = UnitaryNode(Cosh, t)
  def tanh[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]         = UnitaryNode(Tanh, t)
  def exp[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]          = UnitaryNode(Exp, t)
  def log[S, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V]          = UnitaryNode(Log, t)
  def sqrt[S: Number, V](t: AST[NumberOperator, S, V]): AST[NumberOperator, S, V] = BinaryNode(Power, t, ConstantNode(Number.oneHalf[S]))

  extension[S, V](t1: AST[NumberOperator, S, V]) {
    def +(t2: AST[NumberOperator, S, V]): AST[NumberOperator, S, V] = BinaryNode(Plus, t1, t2)
    def -(t2: AST[NumberOperator, S, V]): AST[NumberOperator, S, V] = BinaryNode(Minus, t1, t2)
    def *(t2: AST[NumberOperator, S, V]): AST[NumberOperator, S, V] = BinaryNode(Times, t1, t2)
    def /(t2: AST[NumberOperator, S, V]): AST[NumberOperator, S, V] = BinaryNode(Divided, t1, t2)
    def ^(t2: AST[NumberOperator, S, V]): AST[NumberOperator, S, V] = BinaryNode(Power, t1, t2)
  }

  extension[V](v: V) {
    def variable[S]: AST.VariableNode[NumberOperator, S, V] = VariableNode[NumberOperator, S, V](v)
  }

  extension[S](s: S) {
    def constant[V]: AST.ConstantNode[NumberOperator, S, V] = ConstantNode[NumberOperator, S, V](s)
  }
}
