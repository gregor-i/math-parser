package mathParser

trait Derive[UO, BO, S, V] {
  def derive(node:Node[UO, BO, S, V])
            (variable: V): Node[UO, BO, S, V]
}
