package mathParser

trait Derive[UO, BO, S, V] {
  def derive(node: AbstractSyntaxTree[UO, BO, S, V])(variable: V): AbstractSyntaxTree[UO, BO, S, V]
}
