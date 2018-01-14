package mathParser.slices

// compile your AST instead of evaluating the AST directly.
// This is only intended to be used for tests or single evaluations of the AST
trait Evaluate {
  _: LanguageOperators with AbstractSyntaxTree =>
  
  private[mathParser] def evaluate(node: Node)
                               (variableAssignment: PartialFunction[Symbol, S]): S = {

    def eval(node: Node): S = node match {
      case ConstantNode(value)  => value
      case un : UnitaryNode => un.op.apply(eval(un.child))
      case bn : BinaryNode  => bn.op.apply(eval(bn.childLeft), eval(bn.childRight))
      case Variable(name)   => variableAssignment(name)
    }

    eval(node)
  }
}
