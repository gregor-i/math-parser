package mathParser

import scala.util.Try

object Syntax {
  extension [O, S, V](node: AbstractSyntaxTree[O, S, V]) {
    def evaluate(variableAssignment: V => S)(using evaluate: Evaluate[O, S, V]): S =
      evaluate.evaluate(node)(variableAssignment)

    def optimize(using optimizer: Optimizer[O, S, V]): AbstractSyntaxTree[O, S, V] =
      optimizer.optimize(node)

    def derive(variable: V)(using derive: Derive[O, S, V]): AbstractSyntaxTree[O, S, V] =
      derive.derive(node)(variable)

    def compile[F](using compiler: Compiler[O, S, V, F]): Try[F] =
      compiler.compile(node)
  }
}
