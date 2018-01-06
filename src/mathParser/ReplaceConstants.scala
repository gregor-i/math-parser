package mathParser

import mathParser.AbstractSyntaxTree._

object ReplaceConstants {
  def apply[S, Lang <: Language[S]](node: Node[S, Lang]): Node[S, Lang] = {
    def isConstant(node: Node[S, Lang]): Boolean = node match {
      case Variable(name)             => false
      case UnitaryNode(_, child)      => isConstant(child)
      case BinaryNode(_, left, right) => isConstant(left) && isConstant(right)
      case _                          => true
    }

    def replace(node: Node[S, Lang]): Node[S, Lang] =
      if (isConstant(node))
        Constant(Evaluate(node)(PartialFunction.empty))
      else node match {
        case u@UnitaryNode(_, child)      => u.copy(child = replace(child))
        case b@BinaryNode(_, left, right) => b.copy(childLeft = replace(left), childRight = replace(right))
        case _                            => node
      }

    replace(node)
  }
}
