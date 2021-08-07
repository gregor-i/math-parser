package mathParser

import scala.annotation.tailrec
import mathParser.AbstractSyntaxTree.*

type OptimizationRule[O, S, V] = PartialFunction[AbstractSyntaxTree[O, S, V], AbstractSyntaxTree[O, S, V]]

trait Optimizer[O, S, V] {
  def rules: List[OptimizationRule[O, S, V]]

  @tailrec
  final def optimize(term: AbstractSyntaxTree[O, S, V]): AbstractSyntaxTree[O, S, V] = {
    def applyRules(node: AbstractSyntaxTree[O, S, V]): AbstractSyntaxTree[O, S, V] =
      rules.reduce(_ orElse _).lift(node).getOrElse(node)

    val optimized = term.fold[AbstractSyntaxTree[O, S, V]](
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
  def replaceConstantsRule[O, S, V](using eval: Evaluate[O, S, V]): OptimizationRule[O, S, V] = {
    case BinaryNode(op, ConstantNode(l), ConstantNode(r)) => ConstantNode(eval.executeBinaryOperator(op, l, r))
    case UnitaryNode(op, ConstantNode(l))                 => ConstantNode(eval.executeUnitary(op, l))
  }
}
