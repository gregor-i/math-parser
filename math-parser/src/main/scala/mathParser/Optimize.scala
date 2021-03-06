package mathParser

import scala.annotation.tailrec
import mathParser.AbstractSyntaxTree.*

type OptimizationRule[UO, BO, S, V] = PartialFunction[AbstractSyntaxTree[UO, BO, S, V], AbstractSyntaxTree[UO, BO, S, V]]

trait Optimizer[UO, BO, S, V] {
  def rules: List[OptimizationRule[UO, BO, S, V]]

  @tailrec
  final def optimize(term: AbstractSyntaxTree[UO, BO, S, V]): AbstractSyntaxTree[UO, BO, S, V] = {
    def applyRules(node: AbstractSyntaxTree[UO, BO, S, V]): AbstractSyntaxTree[UO, BO, S, V] =
      rules.reduce(_ orElse _).lift(node).getOrElse(node)

    val optimized = term.fold[AbstractSyntaxTree[UO, BO, S, V]](
      ifConstant = ConstantNode.apply,
      ifUnitary = (op, child) => applyRules(UnitaryNode(op, child)),
      ifBinary = (op, childLeft, childRight) => applyRules(BinaryNode(op, childLeft, childRight)),
      ifVariable = VariableNode.apply
    )

    if (optimized == term)
      optimized
    else
      optimize(optimized)
  }
}

object Optimize {
  def replaceConstantsRule[UO, BO, S, V](using eval: Evaluate[UO, BO, S, V]): OptimizationRule[UO, BO, S, V] = {
    case BinaryNode(op, ConstantNode(l), ConstantNode(r)) => ConstantNode(eval.executeBinaryOperator(op, l, r))
    case UnitaryNode(op, ConstantNode(l))                 => ConstantNode(eval.executeUnitary(op, l))
  }
}
