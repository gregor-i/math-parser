package mathParser.slices

trait ReplaceConstants {
  _: AbstractSyntaxTree with Evaluate =>

  def replaceConstants(node: Node): Node = {
    def isConstant(node: Node): Boolean = node match {
      case Variable(name)             => false
      case UnitaryNode(_, child)      => isConstant(child)
      case BinaryNode(_, left, right) => isConstant(left) && isConstant(right)
      case _                          => true
    }

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
