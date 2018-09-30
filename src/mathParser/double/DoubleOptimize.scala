package mathParser.double
import mathParser.slices.{AbstractSyntaxTree, Optimize}

trait DoubleOptimize extends Optimize{
  _ : AbstractSyntaxTree with DoubleOperators with DoubleSyntaxSugar =>

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
      case BinaryNode(Times, ConstantNode(0d), _) => ConstantNode(0d)
      case BinaryNode(Times, _, ConstantNode(0d)) => ConstantNode(0d)
    },
    {
      case BinaryNode(Times, left, ConstantNode(1d))  => left
      case BinaryNode(Times, ConstantNode(1d), right) => right
    },
    {
      case UnitaryNode(Log, UnitaryNode(Exp, child))  => child
    }
  )
}
