package mathParser

import mathParser.AbstractSyntaxTree._

// compile your AST instead of evaluating the AST direct.
// This is only intended to be used for tests or single executions of the AST
private[mathParser] object Evaluate {
  def apply[Lang <: Language](node: Node[Lang])
                             (variableAssignment: PartialFunction[Symbol, Lang#Skalar]): Lang#Skalar = {

    def evalUnitaryNode(node: UnitaryNode[Lang]): Lang#Skalar = {
      val op = node.op.asInstanceOf[UnitaryOperator[Lang#Skalar]]
      op.apply(eval(node.child))
    }

    def evalBinaryNode(node: BinaryNode[Lang]): Lang#Skalar = {
      val op = node.op.asInstanceOf[BinaryOperator[Lang#Skalar]]
      op.apply(eval(node.childLeft), eval(node.childRight))
    }

    def eval(node: Node[Lang]): Lang#Skalar = node match {
      case Constant(value) => value
      case un : UnitaryNode[Lang] => evalUnitaryNode(un)
      case bn : BinaryNode[Lang] => evalBinaryNode(bn)
      case Variable(name) => variableAssignment(name)
    }

    eval(node)
  }
}
