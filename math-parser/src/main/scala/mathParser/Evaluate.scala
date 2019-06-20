package mathParser


trait Evaluate[UO, BO, S, V] {
  def executeUnitary(uo: UO, s: S): S

  def executeBinaryOperator(bo: BO, left: S, right: S): S

  def evaluate(node: Node[UO, BO, S, V])
              (variableAssignment: V => S): S =
    node.fold[S](identity,
      executeUnitary,
      executeBinaryOperator,
      variableAssignment)
}
