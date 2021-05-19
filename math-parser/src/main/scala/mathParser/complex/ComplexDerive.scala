package mathParser.complex

import mathParser.{Derive}
import mathParser.complex.ComplexUnitaryOperator.*
import mathParser.complex.ComplexBinaryOperator.*
import mathParser.complex.Syntax.*
import mathParser.*

class ComplexDerive[V] extends Derive[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] {
  def derive(term: ComplexNode[V])(variable: V): ComplexNode[V] = {
    def derive(term: ComplexNode[V]): ComplexNode[V] = term match {
      case VariableNode(`variable`)          => one
      case VariableNode(_) | ConstantNode(_) => zero
      case UnitaryNode(op, f) =>
        op match {
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
        }
      case BinaryNode(op, f, g) =>
        op match {
          case Plus    => derive(f) + derive(g)
          case Minus   => derive(f) - derive(g)
          case Times   => (derive(f) * g) + (derive(g) * f)
          case Divided => ((f * derive(g)) - (g * derive(f))) / (g * g)
          case Power   => (f ^ (g - one)) * ((g * derive(f)) + (f * log(f) * derive(g)))
        }
    }

    derive(term)
  }
}
