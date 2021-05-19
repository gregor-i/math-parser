package mathParser.complex

import mathParser.*
import mathParser.complex.Syntax.*
import mathParser.complex.ComplexBinaryOperator.*
import mathParser.complex.ComplexUnitaryOperator.*

class ComplexOptimize[V] extends Optimizer[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] {
 def rules: List[PartialFunction[ComplexNode[V], ComplexNode[V]]] = List(
  Optimize.replaceConstantsRule(using ComplexEvaluate[V]), {
    case UnitaryNode(Neg, UnitaryNode(Neg, child))               => child
    case BinaryNode(Plus, left, ConstantNode(Complex(0d, 0d)))   => left
    case BinaryNode(Plus, ConstantNode(Complex(0d, 0d)), right)  => right
    case BinaryNode(Times, ConstantNode(Complex(0d, 0d)), _)     => zero[V]
    case BinaryNode(Times, _, ConstantNode(Complex(0d, 0d)))     => zero[V]
    case BinaryNode(Times, left, ConstantNode(Complex(1d, 0d)))  => left
    case BinaryNode(Times, ConstantNode(Complex(1d, 0d)), right) => right
    case BinaryNode(Power, left, ConstantNode(Complex(1d, 0d)))  => left
    case BinaryNode(Power, _, ConstantNode(Complex(0d, 0d)))     => one[V]
    case BinaryNode(Power, ConstantNode(Complex(1d, 0d)), _)     => one[V]
    case BinaryNode(Power, ConstantNode(Complex(0d, 0d)), _)     => zero[V]
    case UnitaryNode(Log, UnitaryNode(Exp, child))               => child
    case BinaryNode(Plus, left, UnitaryNode(Neg, child))         => left - child
    case BinaryNode(Minus, left, UnitaryNode(Neg, child))        => left + child
    case BinaryNode(Minus, left, right) if left == right         => zero[V]
    case BinaryNode(Divided, left, right) if left == right       => one[V]
  }
)
}
