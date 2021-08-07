package mathParser

import scala.util.Try

trait Compiler[O, S, V, F] {
  def compile(node: AbstractSyntaxTree[O, S, V]): Try[F]
}
