package mathParser

import scala.util.Try

trait Compiler[UO, BO, S, V, F] {
  def compile(node: Node[UO, BO, S, V]): Try[F]
}
