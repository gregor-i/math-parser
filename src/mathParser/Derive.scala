package mathParser

import mathParser.AbstractSyntaxTree.Node

trait Derive[S, Lang <: Language[S]] {
  def apply(term: Node[S, Lang])(variable: Variable): Node[S, Lang]
}
