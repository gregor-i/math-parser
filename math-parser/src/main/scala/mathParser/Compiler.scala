package mathParser

import scala.util.Try

trait Compiler[UO, BO, S, V, F] {
  def compile(node: AbstractSyntaxTree[UO, BO, S, V]): Try[F]
}
