package mathParser

trait Derive[O, S] {
  def derive[V](node: AbstractSyntaxTree[O, S, V])(variable: V): AbstractSyntaxTree[O, S, V]
}
