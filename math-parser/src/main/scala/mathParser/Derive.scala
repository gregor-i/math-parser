package mathParser

trait Derive[O, S, V] {
  def derive(node: AbstractSyntaxTree[O, S, V])(variable: V): AbstractSyntaxTree[O, S, V]
}
