package mathParser

trait Evaluate[O, S] {
  def executeUnitary(uo: O & UnitaryOperator, s: S): S

  def executeBinaryOperator(bo: O & BinaryOperator, left: S, right: S): S

  def evaluate[V](node: AbstractSyntaxTree[O, S, V])(variableAssignment: V => S): S =
    node.fold[S](identity, executeUnitary, executeBinaryOperator, variableAssignment)
}
