package mathParser.number

import mathParser.{AbstractSyntaxTree, Derive}
import mathParser.AbstractSyntaxTree.*
import mathParser.number.NumberOperator.*
import mathParser.number.NumberUnitaryOperator
import mathParser.number.NumberBinaryOperator
import mathParser.number.NumberSyntax.*

class NumberDerive[S: Number, V] extends Derive[NumberUnitaryOperator, NumberBinaryOperator, S, V] {
  def derive(term: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V])(variable: V): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = {
    val zero = ConstantNode[NumberUnitaryOperator, NumberBinaryOperator, S, V](Number.zero[S])
    val one = ConstantNode[NumberUnitaryOperator, NumberBinaryOperator, S, V](Number.one[S])

    def derive(term: AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V]): AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, S, V] = term match {
      case VariableNode(`variable`)          => one
      case VariableNode(_) | ConstantNode(_) => zero
      case UnitaryNode(op, f) =>
        (op: Any) match {
          case Neg  => neg(derive(f))
          case Sin  => derive(f) * cos(f)
          case Cos  => neg(derive(f) * sin(f))
          case Tan  => derive(f) / (cos(f) * cos(f))
          case Asin => derive(f) / sqrt(one - (f * f))
          case Acos => neg(derive(f)) / sqrt(one - (f * f))
          case Atan => derive(f) / (one + (f * f))
          case Sinh => derive(f) * cosh(f)
          case Cosh => derive(f) * sinh(f)
          case Tanh => derive(f) / (cosh(f) * cosh(f))
          case Exp  => exp(f) * derive(f)
          case Log  => derive(f) / f
          case _ => ???
        }
      case BinaryNode(op, f, g) =>
        (op: Any) match {
          case Plus    => derive(f) + derive(g)
          case Minus   => derive(f) - derive(g)
          case Times   => (derive(f) * g) + (derive(g) * f)
          case Divided => ((f * derive(g)) - (g * derive(f))) / (g * g)
          case Power   => (f ^ (g - one)) * ((g * derive(f)) + (f * log(f) * derive(g)))
          case _ => ???
        }
    }

    derive(term)
  }
}
