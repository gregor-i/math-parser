package mathParser.slices

trait ReplaceConstants {
  _: AbstractSyntaxTree with Evaluate =>

  def replaceConstants(node: Node): Node = {
    def isConstant(node: Node): Boolean =
      node.fold[Boolean](
        _ => true,
        (_, child) => child,
        (_, left, right) => left && right,
        _ => false)

    def replace(node: Node): Node =
      if (isConstant(node))
        ConstantNode(evaluate(node)(PartialFunction.empty))
      else node match {
        case u@UnitaryNode(_, child)      => u.copy(child = replace(child))
        case b@BinaryNode(_, left, right) => b.copy(childLeft = replace(left), childRight = replace(right))
        case _                            => node
      }

    replace(node)
  }
}
