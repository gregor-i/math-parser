package mathParser.slices

import scala.annotation.tailrec

trait Optimize {
  _: AbstractSyntaxTree with LanguageOperators =>

  type OptimizationRule = PartialFunction[Node, Node]

  def optimizationRules: Seq[OptimizationRule]

  def replaceConstantsRule: OptimizationRule  = {
    case BinaryNode(op, ConstantNode(l), ConstantNode(r)) => ConstantNode(op.apply(l, r))
    case UnitaryNode(op, ConstantNode(l)) => ConstantNode(op.apply(l))
  }

  private def applyRules: Node => Node = {
    def loop(node: Node, rules: Seq[OptimizationRule]): Node =
      if (rules.isEmpty)
        node
      else rules.head.applyOrElse(node, (_:Node) => loop(node, rules.tail))

    node => loop(node, optimizationRules)
  }


  @tailrec
  final def optimize(term: Node): Node = {
    val optimized = term.fold[Node](
      ifConstant = ConstantNode.apply,
      ifUnitary = (op, child) => applyRules(UnitaryNode(op, child)),
      ifBinary = (op, childLeft, childRight) => applyRules(BinaryNode(op, childLeft, childRight)),
      ifVariable = Variable.apply
    )

    if(optimized == term)
      optimized
    else
      optimize(optimized)
  }
}
