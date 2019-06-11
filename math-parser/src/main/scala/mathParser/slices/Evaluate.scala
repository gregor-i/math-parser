package mathParser.slices

// compile your AST instead of evaluating the AST directly.
// This is only intended to be used for tests or single evaluations of the AST
trait Evaluate {
  _: LanguageOperators with AbstractSyntaxTree =>

  def evaluate(node: Node)
              (variableAssignment: PartialFunction[Symbol, S]): S =
    node.fold[S](identity,
      (op, child) => op.apply(child),
      (op, left, right) => op.apply(left, right),
      variableAssignment)
}
