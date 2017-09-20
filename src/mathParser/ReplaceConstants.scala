package mathParser

import mathParser.AbstractSyntaxTree._

object ReplaceConstants {
  def apply[Lang <: Language](node: Node[Lang]): Node[Lang] = {
    def mayBeConstant(node: Node[Lang]): Boolean = node match {
      case Variable(name)             => false
      case UnitaryNode(_, child)      => mayBeConstant(child)
      case BinaryNode(_, left, right) => mayBeConstant(left) && mayBeConstant(right)
      case _                          => true
    }

    def replace(node: Node[Lang]): Node[Lang] =
      if (mayBeConstant(node))
        Constant(Evaluate(node)(PartialFunction.empty))
      else node match {
        case u@UnitaryNode(_, child)      => u.copy(child = replace(child))
        case b@BinaryNode(_, left, right) => b.copy(childLeft = replace(left), childRight = replace(right))
        case _                            => node
      }

    replace(node)
  }
}
