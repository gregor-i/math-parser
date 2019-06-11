package mathParser.algebra

import mathParser.slices.{AbstractSyntaxTree, Optimize}

trait SpireOptimize[A] extends Optimize{
  _ : AbstractSyntaxTree with SpireOperators[A] with SpireSyntaxSugar[A] =>

  override def optimizationRules: Seq[OptimizationRule] = List(
    replaceConstantsRule,
    {
      case UnitaryNode(Neg, UnitaryNode(Neg, child)) => child
    },
    {
      case BinaryNode(Plus, left, ConstantNode(0d))  => left
      case BinaryNode(Plus, ConstantNode(0d), right) => right
    },
    {
      case BinaryNode(Times, ConstantNode(0d), _) => zero
      case BinaryNode(Times, _, ConstantNode(0d)) => zero
    },
    {
      case BinaryNode(Times, left, ConstantNode(1d))  => left
      case BinaryNode(Times, ConstantNode(1d), right) => right
    },
    {
      case UnitaryNode(Log, UnitaryNode(Exp, child))  => child
    },
    {
      case BinaryNode(Plus, left, UnitaryNode(Neg, child)) => left - child
      case BinaryNode(Minus, left, UnitaryNode(Neg, child)) => left + child
    },
    {
      case BinaryNode(Minus, left, right) if left == right => zero
    },
    {
      case BinaryNode(Divided, left, right) if left == right => one
    }
  )
}
