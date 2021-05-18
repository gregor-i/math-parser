package mathParser

import scala.util.Try

object Syntax {
  extension [UO, BO, S, V](node: Node[UO, BO, S, V]) {
    def evaluate(variableAssignment: V => S)(using evaluate: Evaluate[UO, BO, S, V]): S =
      evaluate.evaluate(node)(variableAssignment)

    def optimize(using optimizer: Optimizer[UO, BO, S, V]): Node[UO, BO, S, V] =
      optimizer.optimize(node)

    def derive(variable: V)(using derive: Derive[UO, BO, S, V]): Node[UO, BO, S, V] =
      derive.derive(node)(variable)

    def compile[F](using compiler: Compiler[UO, BO, S, V, F]): Try[F] =
      compiler.compile(node)
  }
}
