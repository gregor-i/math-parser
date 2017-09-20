package mathParser

import mathParser.AbstractSyntaxTree._

// compile your AST instead of evaluating the AST direct.
// This is only intended to be used for tests or single executions of the AST
private[mathParser] object Evaluate {
  def apply[S, Lang <: Language[S]](node: Node[S, Lang])
                             (variableAssignment: PartialFunction[Symbol, S]): S = {

    def eval(node: Node[S, Lang]): S = node match {
      case Constant(value)        => value
      case un : UnitaryNode[S, Lang] => un.op.apply(eval(un.child))
      case bn : BinaryNode[S, Lang]  => bn.op.apply(eval(bn.childLeft), eval(bn.childRight))
      case Variable(name)         => variableAssignment(name)
    }

    eval(node)
  }
}
